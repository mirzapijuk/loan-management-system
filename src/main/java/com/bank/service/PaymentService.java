package com.bank.service;

import com.bank.model.Payment;
import com.bank.model.PaymentRequest;

import java.util.List;

public interface PaymentService {

    List<Payment> getPaymentsByParty(Long partyNumber);

    Payment setPayment(PaymentRequest paymentRequest);
}
