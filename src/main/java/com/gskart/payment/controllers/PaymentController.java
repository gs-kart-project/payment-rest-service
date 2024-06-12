package com.gskart.payment.controllers;

import com.gskart.payment.DTOs.request.InitiatePaymentLinkRequest;
import com.gskart.payment.DTOs.responses.PaymentLinkResponse;
import com.gskart.payment.data.entities.PaymentDetail;
import com.gskart.payment.mappers.PaymentMapper;
import com.gskart.payment.services.IPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final IPaymentService paymentService;
    private final PaymentMapper paymentMapper;

    public PaymentController(IPaymentService paymentService, PaymentMapper paymentMapper) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
    }

    // Returns payment link of payment gateway
    @PostMapping("/createLink")
    public ResponseEntity<List<PaymentLinkResponse>> initiatePaymentLink(@RequestBody InitiatePaymentLinkRequest initiatePaymentLinkRequest){
        PaymentDetail paymentDetail = paymentMapper.initializePaymentRequestToPaymentDetailsEntity(initiatePaymentLinkRequest);
        paymentDetail = paymentService.initiatePayment(paymentDetail);

        List<PaymentLinkResponse> paymentLinkResponses = paymentDetail.getPayments().stream().map(payment->{
            PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
            paymentLinkResponse.setPaymentId(payment.getId());
            paymentLinkResponse.setPaymentLink(payment.getPaymentLink());
            paymentLinkResponse.setPaymentLinkId(payment.getPaymentLinkId());
            return paymentLinkResponse;
        }).toList();

        return ResponseEntity.ok(paymentLinkResponses);
    }

    @PutMapping("/updateStatus")
    public void updatePaymentStatus(@RequestParam String paymentId, @RequestParam String paymentStatus){

    }


}
