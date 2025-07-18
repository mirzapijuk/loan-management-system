package com.bank.conroller;

import com.bank.model.Installment;
import com.bank.model.Loan;
import com.bank.model.LoanRequest;
import com.bank.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/loan")
@AllArgsConstructor
public class LoanRest {

    private LoanService loanService;

    @Operation(summary = "Get all loans.")
    @GetMapping
    public ResponseEntity<List<Loan>> getLoans() {
        return ResponseEntity.ok(loanService.getLoans());
    }

    @Operation(summary = "Get loan by id.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @Operation(summary = "Create loan.")
    @PostMapping
    public ResponseEntity<Loan> setLoan(@RequestBody final LoanRequest loanRequest) {
        return ResponseEntity.ok(loanService.setLoan(loanRequest));
    }

    @Operation(summary = "Create loan amortization.")
    @PostMapping(value = "/amortization")
    public ResponseEntity<List<Installment>> getAmortizationPlan(@RequestParam final Long partyNumber) {
        return ResponseEntity.ok(loanService.getAmortizationPlan(partyNumber));
    }

    @Operation(summary = "Get installment plan.")
    @GetMapping(value = "/schedule/pdf")
    public ResponseEntity<byte[]> getLoanSchedulePdf(@RequestParam final Long partyNumber) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=loan_schedule.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(loanService.getLoanSchedulePdf(partyNumber));
    }

    @Operation(summary = "Send installment plan to customer.")
    @GetMapping(value = "/send-installment-to-customer")
    public ResponseEntity<String> sendInstallmentToCustomer(@RequestParam final Long partyNumber) {
        return ResponseEntity.ok(loanService.sendInstallmentToCustomer(partyNumber));
    }
}
