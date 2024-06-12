package com.gskart.payment.DTOs;

import lombok.Data;

@Data
public class PaymentDto {
    private Double amount;
    private String paidBy;
}
