package com.bank.conroller;

import com.bank.model.Payment;
import com.bank.model.PaymentRequest;
import com.bank.service.InstallmentService;
import com.bank.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payment")
@AllArgsConstructor
public class PaymentRest {

    private PaymentService paymentService;
    private InstallmentService installmentService;

    @Operation(summary = "Get all payments.")
    @GetMapping
    public ResponseEntity<List<Payment>> getPaymentsByParty(@RequestParam final Long partyNumber) {
        return ResponseEntity.ok(paymentService.getPaymentsByParty(partyNumber));
    }

    @Operation(summary = "Initiate payment.")
    @PostMapping
    public ResponseEntity<Payment> setPayment(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.setPayment(paymentRequest));
    }
}
