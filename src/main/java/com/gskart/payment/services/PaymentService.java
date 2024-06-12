package com.gskart.payment.services;

import com.gskart.payment.data.entities.Address;
import com.gskart.payment.data.entities.Payment;
import com.gskart.payment.data.entities.PaymentDetail;
import com.gskart.payment.data.entities.PhoneNumber;
import com.gskart.payment.data.repositories.PaymentDetailRepository;
import com.gskart.payment.data.repositories.PaymentRepository;
import com.gskart.payment.gateways.PaymentGatewayStrategy;
import com.gskart.payment.gateways.exceptions.PaymentGatewayException;
import com.gskart.payment.security.models.GSKartResourceServerUserContext;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService{
    private final PaymentDetailRepository paymentDetailRepository;
    private final GSKartResourceServerUserContext gskartResourceServerUserContext;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayStrategy paymentGatewayStrategy;

    public PaymentService(PaymentDetailRepository paymentDetailRepository, GSKartResourceServerUserContext gskartResourceServerUserContext, PaymentRepository paymentRepository, PaymentGatewayStrategy paymentGatewayStrategy) {
        this.paymentDetailRepository = paymentDetailRepository;
        this.gskartResourceServerUserContext = gskartResourceServerUserContext;
        this.paymentRepository = paymentRepository;
        this.paymentGatewayStrategy = paymentGatewayStrategy;
    }

    @Override
    public PaymentDetail initiatePayment(PaymentDetail paymentDetail){
        paymentDetail.setStatus(PaymentDetail.Status.IN_PR0GRESS);
        paymentDetail.setCreatedBy(gskartResourceServerUserContext.getGskartResourceServerUser().getUsername());
        paymentDetail.setCreatedOn(OffsetDateTime.now(ZoneOffset.UTC));
        Payment.Status initPaymentStatus = Payment.Status.PENDING_INITIATE;
        if(paymentDetail.getMethod().equals(PaymentDetail.Method.CASH_ON_DELIVERY)){
            initPaymentStatus = Payment.Status.INITIATED;
        }
        if(paymentDetail.getBillingContact() != null){
            paymentDetail.getBillingContact().setCreatedBy(gskartResourceServerUserContext.getGskartResourceServerUser().getUsername());
            paymentDetail.getBillingContact().setCreatedOn(OffsetDateTime.now(ZoneOffset.UTC));
            if(paymentDetail.getBillingContact().getAddresses() != null &&
                    !paymentDetail.getBillingContact().getAddresses().isEmpty()){
                for(Address address : paymentDetail.getBillingContact().getAddresses()){
                    address.setCreatedBy(gskartResourceServerUserContext.getGskartResourceServerUser().getUsername());
                    address.setCreatedOn(OffsetDateTime.now(ZoneOffset.UTC));
                }
            }
            if(paymentDetail.getBillingContact().getPhoneNumbers() != null && !paymentDetail.getBillingContact().getPhoneNumbers().isEmpty()){
                for(PhoneNumber phoneNumber : paymentDetail.getBillingContact().getPhoneNumbers()){
                    phoneNumber.setCreatedBy(gskartResourceServerUserContext.getGskartResourceServerUser().getUsername());
                    phoneNumber.setCreatedOn(OffsetDateTime.now(ZoneOffset.UTC));
                }
            }
        }
        for(Payment payment : paymentDetail.getPayments()){
            payment.setCreatedBy(gskartResourceServerUserContext.getGskartResourceServerUser().getUsername());
            payment.setCreatedOn(OffsetDateTime.now(ZoneOffset.UTC));
            payment.setStatus(initPaymentStatus);
        }
        paymentDetailRepository.save(paymentDetail);

        for(Payment payment : paymentDetail.getPayments()){
            createPaymentLink(payment);
        }

        return paymentDetail;
    }

    private void createPaymentLink(Payment payment) {
        payment.setModifiedBy(gskartResourceServerUserContext.getGskartResourceServerUser().getUsername());
        payment.setModifiedOn(OffsetDateTime.now(ZoneOffset.UTC));
        try {
            payment.setStatus(Payment.Status.INITIATED);
            com.gskart.payment.gateways.DTOs.PaymentLinkResponse pgPaymentLinkResponse = paymentGatewayStrategy.getPaymentGateway().generatePaymentSession(payment.getAmount(), payment.getId());
            payment.setPaymentLink(pgPaymentLinkResponse.getUrl());
            payment.setPaymentLinkId(pgPaymentLinkResponse.getId());
        }
        catch (PaymentGatewayException paymentGatewayException){
            paymentGatewayException.printStackTrace();
            payment.setStatus(Payment.Status.PAYMENT_LINK_CREATION_FAILED);
            payment.setErrorMessage("Error occurred while generating payment link");
        }
        payment = paymentRepository.save(payment);
    }

    @Override
    public PaymentDetail getPaymentDetailById(int id){
        return null;
    }

    @Override
    public boolean updatePayment(String paymentId, String status){
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if(paymentOptional.isEmpty()){
            return false;
        }
        Payment payment = paymentOptional.get();
        payment.setStatus(Payment.Status.valueOf(status));
        payment.setModifiedBy(gskartResourceServerUserContext.getGskartResourceServerUser().getUsername());
        payment.setModifiedOn(OffsetDateTime.now(ZoneOffset.UTC));
        paymentRepository.save(payment);
        return true;
    }

    @Override
    public String initiatePayment(String paymentId){
        return null;
    }
}
