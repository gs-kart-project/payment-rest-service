package com.gskart.payment.controllers;

import com.gskart.payment.data.entities.Payment;
import com.gskart.payment.services.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment-status")
public class PaymentStatusController {

    private final PaymentService paymentService;

    public PaymentStatusController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public String updatePaymentStatus(@RequestParam(name = "status") String status, @RequestParam(name="paymentId") String paymentId, Model model){

        this.paymentService.updatePayment(paymentId, status);

        model.addAttribute("status", status);
        if(status.equalsIgnoreCase(Payment.Status.PAYMENT_SUCCESSFUL.name())){
            return "paymentSuccess";
        }

        if(status.equalsIgnoreCase(Payment.Status.PAYMENT_FAILED.name())){
            return "paymentFailure";
        }

        return "paymentCancelled";
    }
}
