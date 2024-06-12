package com.gskart.payment.mappers;

import com.gskart.payment.DTOs.request.InitiatePaymentLinkRequest;
import com.gskart.payment.data.entities.Payment;
import com.gskart.payment.data.entities.PaymentDetail;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public PaymentDetail initializePaymentRequestToPaymentDetailsEntity(InitiatePaymentLinkRequest paymentRequest) {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setOrderId(paymentRequest.getOrderId());
        paymentDetail.setAmount(paymentRequest.getAmount());
        paymentDetail.setBillingContact(paymentRequest.getBillingContact());
        paymentDetail.setPayments(paymentRequest.getPayments().stream().map(pr->{
            Payment payment = new Payment();
            payment.setAmount(pr.getAmount());
            payment.setPaidBy(pr.getPaidBy());
            return payment;
        }).toList());
        paymentDetail.setMethod(paymentRequest.getMethod());
        return paymentDetail;
    }
}
