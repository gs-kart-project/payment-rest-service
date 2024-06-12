package com.gskart.payment.data.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity(name = "payments")
public class Payment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private PaymentDetail paymentDetail;

    private Double amount;
    private String paidBy;
    private OffsetDateTime paidOn;
    private String errorMessage;
    private String paymentLinkId;
    @Column(length = 1024)
    private String paymentLink;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING_INITIATE,
        INITIATED,
        PAYMENT_SUCCESSFUL,
        PAYMENT_FAILED,
        PAYMENT_LINK_CREATION_FAILED
    }
}
