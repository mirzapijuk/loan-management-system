package com.bank.service.impl;

import com.bank.model.Installment;
import com.bank.model.Loan;
import com.bank.model.LoanRequest;
import com.bank.repository.dao.LoanDAO;
import com.bank.repository.entity.CustomerEntity;
import com.bank.repository.entity.InstallmentEntity;
import com.bank.repository.entity.LoanEntity;
import com.bank.repository.entity.PartyEntity;
import com.bank.security.SecurityUtils;
import com.bank.service.*;
import com.bank.service.mapper.CustomerMapper;
import com.bank.service.mapper.LoanMapper;
import com.bank.service.validation.InstallmentValidation;
import com.bank.service.validation.PartyValidation;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanDAO loanDAO;
    private final CustomerService customerService;
    private final LoanMapper loanMapper;
    private final CustomerMapper customerMapper;
    private final PartyService partyService;
    private final PartyValidation partyValidation;
    private final InstallmentService installmentService;
    private final InstallmentValidation installmentValidation;
    private final EmailService emailService;
    private final SecurityUtils securityUtils;

    @Override
    public List<Loan> getLoans() {
        List<LoanEntity> entities = loanDAO.findAll();
        return loanMapper.entitiesToDtos(entities);
    }

    @Override
    public Loan getLoanById(final Long id) {
        LoanEntity entity = loanDAO.findById(id);
        return loanMapper.entityToDto(entity);
    }

    @Override
    public LoanEntity getLoanByPartyNumber(final Long partyNumber) {
        partyValidation.validatePartyExistence(partyNumber);
        return loanDAO.getLoanByPartyNumber(partyNumber);
    }

    @Override
    public Loan setLoan(final LoanRequest loanRequest) {
        LoanEntity entity = loanMapper.requestToEntity(loanRequest);
        PartyEntity party = partyService.setParty(loanRequest.getPartyRequest());
        CustomerEntity customer = customerMapper.dtoToEntity(customerService.getCustomerByRegistrationNo(loanRequest.getClientRegNo()));
        entity.setParty(party);
        entity.setCustomer(customer);
        entity.setCreated(LocalDateTime.now());
        entity.setCreatedBy(securityUtils.getCurrentUsername());
        return loanMapper.entityToDto(loanDAO.save(entity));
    }

    @Override
    @Transactional
    public void updateRemainingDbtAmount(final Long loanId, final BigDecimal amount) {
        LoanEntity loanEntity = loanDAO.findById(loanId);
        BigDecimal monthlyRate = loanEntity.getInterestRate().divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
        BigDecimal remainingDbtAmount = loanEntity.getRemainingDebtAmount();
        BigDecimal interest = remainingDbtAmount.multiply(monthlyRate).setScale(2, RoundingMode.HALF_UP);
        loanEntity.setRemainingDebtAmount(remainingDbtAmount.subtract(amount.subtract(interest)));
        loanEntity.setModified(LocalDateTime.now());
        loanEntity.setModifiedBy(securityUtils.getCurrentUsername());
        loanDAO.merge(loanEntity);
    }

    @Override
    public List<Installment> getAmortizationPlan(final Long partyNumber) {
        LoanEntity loanEntity = getLoanByPartyNumber(partyNumber);
        installmentValidation.validateInstallment(loanEntity.getId());
        List<InstallmentEntity> installmentEntityList = installmentService.setInstallment(generateSchedule(loanEntity));
        List<Installment> installments = installmentEntityList.stream()
                        .map(i -> new Installment(
                                i.getInstallmentNumber(),
                                i.getLoan().getCustomer().getRegistrationNumber(),
                                i.getLoan().getCustomer().getFullName(),
                                i.getLoan().getParty().getPartyNumber(),
                                i.getDueDate(),
                                i.getAmount()
                        )).collect(Collectors.toUnmodifiableList());
        return installments;
    }

    @Override
    public byte[] getLoanSchedulePdf(final Long partyNumber) {
        LoanEntity loanEntity = getLoanByPartyNumber(partyNumber);
        installmentValidation.validateInstallmentExistence(loanEntity.getId());
        List<InstallmentEntity> installmentEntities = installmentService.getInstallmentsByLoanId(loanEntity.getId());
        List<Installment> installments = installmentEntities.stream()
                .map(i -> new Installment(
                        i.getInstallmentNumber(),
                        i.getLoan().getCustomer().getRegistrationNumber(),
                        i.getLoan().getCustomer().getFullName(),
                        i.getLoan().getParty().getPartyNumber(),
                        i.getDueDate(),
                        i.getAmount()
                )).collect(Collectors.toUnmodifiableList());
        return generateLoanSchedulePdf(installments, partyNumber, loanEntity.getCustomer().getFullName());
    }

    @Override
    public String sendInstallmentToCustomer(final Long partyNumber) {
        byte[] loanSchedulePdf = getLoanSchedulePdf(partyNumber);
        LoanEntity loan = getLoanByPartyNumber(partyNumber);

        try {
            String email = loan.getCustomer().getEmail();
            String name = loan.getCustomer().getFullName();
            emailService.sendLoanSchedulePdf(email, loanSchedulePdf, name);
            return "Email sent successfully.";
        } catch (Exception e) {
            return "Failed to send email: " + e.getMessage();
        }
    }

    private List<InstallmentEntity> generateSchedule(LoanEntity loan) {
        BigDecimal principal = loan.getApprovedAmount();
        int months = loan.getRepaymentPeriodMonths();
        BigDecimal annualInterestRate = loan.getInterestRate();

        // Monthly interest rate as decimal (e.g. 8% -> 0.006666...)
        BigDecimal monthlyRate = annualInterestRate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);

        // Annuity formula
        BigDecimal onePlusRPowerN = BigDecimal.valueOf(Math.pow(1 + monthlyRate.doubleValue(), months));
        BigDecimal numerator = principal.multiply(monthlyRate).multiply(onePlusRPowerN);
        BigDecimal denominator = onePlusRPowerN.subtract(BigDecimal.ONE);
        BigDecimal monthlyPayment = numerator.divide(denominator, 2, RoundingMode.HALF_UP);

        List<InstallmentEntity> installments = new ArrayList<>();
        LocalDate startDate = loan.getApprovalDate().plusMonths(1);

        for (int i = 0; i < months; i++) {
            InstallmentEntity inst = new InstallmentEntity();
            inst.setLoan(loan);
            inst.setInstallmentNumber(i + 1);
            inst.setDueDate(startDate.plusMonths(i));
            inst.setAmount(monthlyPayment);
            installments.add(inst);
        }

        return installments;
    }

    private byte[] generateLoanSchedulePdf(List<Installment> installments, Long partyNum, String client) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font subtitleFont = new Font(Font.HELVETICA, 12);
            Font tableFont = new Font(Font.HELVETICA, 12);

            Paragraph title = new Paragraph("Loan Repayment Schedule", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // empty line

            Paragraph subtitle = new Paragraph("for client " + client + ", loan: " + partyNum, subtitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.5f, 2f, 2f});

            // Header
            Stream.of("No", "Due Date", "Amount")
                    .forEach(header -> {
                        PdfPCell cell = new PdfPCell(new Phrase(header, tableFont));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor(Color.LIGHT_GRAY);
                        table.addCell(cell);
                    });

            // Content
            for (Installment inst : installments) {
                PdfPCell cell1 = new PdfPCell(new Phrase(String.valueOf(inst.getInstallmentNumber()), tableFont));
                cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell1);

                PdfPCell cell2 = new PdfPCell(new Phrase(inst.getDueDate().toString(), tableFont));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell2);

                PdfPCell cell3 = new PdfPCell(new Phrase(format(inst.getAmount()), tableFont));
                cell3.setHorizontalAlignment(Element.ALIGN_RIGHT); // Amount right-aligned
                table.addCell(cell3);
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }

        return out.toByteArray();
    }

    private String format(BigDecimal value) {
        return value != null ? value.setScale(2, BigDecimal.ROUND_HALF_UP).toString() : "-";
    }

}

