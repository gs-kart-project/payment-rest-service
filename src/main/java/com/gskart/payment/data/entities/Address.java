package com.gskart.payment.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "address")
@Data
public class Address extends BaseEntity {
    private String doorNumber;
    private String street;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zip;
    private String country;
}
