package com.gskart.payment.data.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity(name = "paymentDetails")
public class PaymentDetail extends BaseEntity {
    private String orderId;
    private Double amount;

    @OneToOne(cascade = CascadeType.ALL)
    private Contact billingContact;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentDetail", cascade = CascadeType.ALL)
    private List<Payment> payments;
    private Double balanceAmount;

    @Enumerated(EnumType.STRING)
    private Method method;

    @Enumerated(EnumType.STRING)
    private Status status;

    private OffsetDateTime completedOn;

    public enum Method {
        CASH_ON_DELIVERY,
        ONLINE
    }

    public enum Status{
        IN_PR0GRESS,
        COMPLETED
    }
}
