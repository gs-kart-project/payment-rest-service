package com.gskart.payment.data.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.UUIDGenerator;
import org.hibernate.id.UUIDHexGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor

//Db Attributes
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String createdBy;
    private OffsetDateTime createdOn;
    private  String modifiedBy;
    private OffsetDateTime modifiedOn;
}