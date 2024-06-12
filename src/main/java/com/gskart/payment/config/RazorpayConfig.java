package com.gskart.payment.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RazorpayConfig {

    @Value("${paymentGateway.razorpay.id}")
    private String razorpayId;

    @Value("${paymentGateway.razorpay.secret}")
    private String razorpaySecret;

//    @Bean
    public RazorpayClient getRazorpayClient() throws RazorpayException {
        return new RazorpayClient(razorpayId, razorpaySecret);
    }
}