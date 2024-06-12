package com.gskart.payment.gateways.exceptions;

public class PaymentGatewayException extends Exception {
    public PaymentGatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}
