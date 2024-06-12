package com.gskart.payment.gateways;

import com.gskart.payment.gateways.DTOs.PaymentLinkResponse;
import com.gskart.payment.gateways.exceptions.PaymentGatewayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentGateway implements IPaymentGateway {
    @Value("${paymentGateway.stripe.secretKey}")
    private String secretKey;

    @Value("${paymentGateway.redirectHost}")
    private String redirectHost;

    public StripePaymentGateway() {

    }

    @Override
    public PaymentLinkResponse generatePaymentLink(double amount) throws PaymentGatewayException {
        Stripe.apiKey = secretKey;
        try {
        Price price = generatePrice(amount);
        PaymentLinkCreateParams params = PaymentLinkCreateParams.builder()
                .addLineItem(PaymentLinkCreateParams.LineItem.builder()
                        .setPrice(price.getId())
                        .setQuantity(1L)
                        .build())
                .build();

            PaymentLink paymentLink = PaymentLink.create(params);
            PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
            paymentLinkResponse.setId(paymentLink.getId());
            paymentLinkResponse.setUrl(paymentLink.getUrl());
            return paymentLinkResponse;
        } catch (StripeException e) {
            throw new PaymentGatewayException("Exception occurred while creating payment link.", e);
        }
    }

    @Override
    public PaymentLinkResponse generatePaymentSession(double amount, String paymentId) throws PaymentGatewayException {
        Stripe.apiKey = secretKey;
        try {
            String successUrl = String.format("%s/payment-status?status=PAYMENT_SUCCESSFUL&paymentId=%s", redirectHost, paymentId);
            String failureUrl = String.format("%s/payment-status?status=PAYMENT_FAILED&paymentId=%s", redirectHost, paymentId);;
            Price price = generatePrice(amount);
            PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
            SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
                .setSuccessUrl(successUrl)
                .addLineItem(SessionCreateParams.LineItem.builder()
                    .setPrice(price.getId())
                    .setQuantity(1L)
                    .build())
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureUrl)
                .build();
            com.stripe.model.checkout.Session checkoutSession = Session.create(sessionCreateParams);
            paymentLinkResponse.setId(checkoutSession.getId());
            paymentLinkResponse.setUrl(checkoutSession.getUrl());
            return paymentLinkResponse;
        } catch (StripeException e) {
            throw new PaymentGatewayException("Exception occurred while creating payment link.", e);
        }
    }

    private Price generatePrice(double amount) throws StripeException {
        // As unit amount is considered in Paise / Cents X'ing by 100
        Long unitAmount = Math.round(amount) * 100;
        PriceCreateParams params = PriceCreateParams.builder()
                .setCurrency("INR")
                .setUnitAmount(unitAmount)
                .setProductData(PriceCreateParams.ProductData.builder().setName("Gold Plan").build())
                .build();
        return Price.create(params);
    }
}
