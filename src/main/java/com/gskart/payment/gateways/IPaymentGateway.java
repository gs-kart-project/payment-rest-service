package com.gskart.payment.gateways;

import com.gskart.payment.gateways.DTOs.PaymentLinkResponse;
import com.gskart.payment.gateways.exceptions.PaymentGatewayException;

public interface IPaymentGateway {
    PaymentLinkResponse generatePaymentLink(double amount) throws PaymentGatewayException;

    PaymentLinkResponse generatePaymentSession(double amount, String paymentId) throws PaymentGatewayException;
}
