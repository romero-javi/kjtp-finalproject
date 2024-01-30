package com.javi.kjtpfinalproject.patterns.checkoutchain;

import com.javi.kjtpfinalproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckoutChain {
    private final UserService userService;
    private final ProductAvailabilityHandler productAvailabilityHandler;
    private final FundsAvailabilityHandler fundsAvailabilityHandler;
    private final OrderCreationHandler orderCreationHandler;
    private final PaymentHandler paymentHandler;
    private final RemoveCheckoutHandler removeCheckoutHandler;

    public void processCheckout(String userId) {
        CheckoutHandler checkoutHandler = productAvailabilityHandler;

        checkoutHandler
                .setNextHandler(fundsAvailabilityHandler)
                .setNextHandler(orderCreationHandler)
                .setNextHandler(paymentHandler)
                .setNextHandler(removeCheckoutHandler);

        checkoutHandler.handleCheckout(userService.findUserCheckoutByUser(userId));
    }
}
