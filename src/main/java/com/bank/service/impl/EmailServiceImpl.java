package com.bank.service.impl;

import com.bank.model.CriticalCustomer;
import com.bank.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    @Override
    public void sendLoanSchedulePdf(String toEmail, byte[] pdfData, String customerName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("Loan Repayment Schedule");
        helper.setText("Dear " + customerName + ",\n\nPlease find attached your loan repayment schedule.\n\nBest regards,\nYour Bank");

        helper.addAttachment("loan-schedule.pdf", new ByteArrayResource(pdfData));

        mailSender.send(message);
    }

    @Override
    public void sendPaymentWarning(CriticalCustomer customer) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(customer.getEmail());
        helper.setSubject("Payment Loan Warning");
        helper.setText("Dear " + customer.getFullName() + ",\n\nPlease pay your loan installment as soon as possible to avoid additional charges.\n\nBest regards,\nYour Bank");

        mailSender.send(message);
    }
}
