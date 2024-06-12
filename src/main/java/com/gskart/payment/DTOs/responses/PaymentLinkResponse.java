package com.gskart.payment.DTOs.responses;

import lombok.Data;

@Data
public class PaymentLinkResponse {
    String paymentId;
    String paymentLinkId;
    String paymentLink;
}
