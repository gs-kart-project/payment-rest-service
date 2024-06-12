package com.gskart.payment.data.repositories;

import com.gskart.payment.data.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {

}
