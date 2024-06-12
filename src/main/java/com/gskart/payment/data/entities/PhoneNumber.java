package com.gskart.payment.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "phoneNumbers")
@Data
public class PhoneNumber extends BaseEntity {
    private String number;
    private NumberType type;
    private String countryCode;
    public enum NumberType {
        MOBILE,
        PHONE
    }
}
