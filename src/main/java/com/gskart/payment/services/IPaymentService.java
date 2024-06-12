package com.gskart.payment.services;

import com.gskart.payment.data.entities.Payment;
import com.gskart.payment.data.entities.PaymentDetail;

public interface IPaymentService {

    PaymentDetail initiatePayment(PaymentDetail paymentDetail);

    PaymentDetail getPaymentDetailById(int id);

    boolean updatePayment(String paymentId, String status);

    String initiatePayment(String paymentId);
}

