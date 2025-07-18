package com.bank.service;

import com.bank.model.CriticalCustomer;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendLoanSchedulePdf(String toEmail, byte[] pdfData, String customerName) throws MessagingException;

    void sendPaymentWarning(CriticalCustomer customer) throws MessagingException;

}
