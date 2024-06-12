package com.gskart.payment.gateways;

import com.gskart.payment.gateways.DTOs.PaymentLinkResponse;
import com.gskart.payment.gateways.exceptions.PaymentGatewayException;
import org.springframework.stereotype.Service;

@Service
public class RazorpayPaymentGateway implements  IPaymentGateway {
    @Override
    public PaymentLinkResponse generatePaymentLink(double amount) {
        return new PaymentLinkResponse();
    }

    @Override
    public PaymentLinkResponse generatePaymentSession(double amount, String paymentId) throws PaymentGatewayException {
        return null;
    }
}
