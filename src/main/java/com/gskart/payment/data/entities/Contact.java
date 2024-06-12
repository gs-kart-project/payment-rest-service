package com.gskart.payment.data.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "contacts")
@Data
public class Contact extends BaseEntity {
    private String firstName;
    private String lastName;
    private String emailId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Address> addresses;
}
