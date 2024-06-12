package com.gskart.payment.data.repositories;

import com.gskart.payment.data.entities.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, String> {

}
