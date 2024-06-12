package com.gskart.payment.DTOs.request;

import com.gskart.payment.DTOs.PaymentDto;
import com.gskart.payment.data.entities.Contact;
import com.gskart.payment.data.entities.PaymentDetail;
import lombok.Data;

import java.util.List;

@Data
public class InitiatePaymentLinkRequest {
    private String orderId;
    private Double amount;
    private Contact billingContact;
    private List<PaymentDto> payments;
    private PaymentDetail.Method method;
}
