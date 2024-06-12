package com.gskart.payment.gateways;

import org.springframework.stereotype.Service;

/**
 * Chooses a payment gateway from the available implementations based on a strategy
 * May be based on user input (OR)
 * Randomly choosing
 */
@Service
public class PaymentGatewayStrategy {
    private final RazorpayPaymentGateway razorpayPaymentGateway;
    private final StripePaymentGateway stripePaymentGateway;


    public PaymentGatewayStrategy(RazorpayPaymentGateway razorpayPaymentGateway, StripePaymentGateway stripePaymentGateway) {
        this.razorpayPaymentGateway = razorpayPaymentGateway;
        this.stripePaymentGateway = stripePaymentGateway;
    }

    public IPaymentGateway getPaymentGateway() {
        // Returning Stripe payment gateway. After integration with Stripe payment gateway, can build the logic to decide between the payment gateways.
        return stripePaymentGateway;
    }
}
