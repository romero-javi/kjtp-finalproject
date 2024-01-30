package com.javi.kjtpfinalproject.patterns.checkoutchain;

import com.javi.kjtpfinalproject.entities.Checkout;

public abstract class CheckoutHandler {
    private CheckoutHandler nextHandler;

    /**
     * @param checkout - Checkout to handle
     * This method ensures that if doHandleCheckout() returns true the request is considered handled,
     * in the current scenario the request is considered successfully handled if every handle handles the request
     */
    public void handleCheckout(Checkout checkout) {
        if(doHandleCheckout(checkout))
            return;

        nextHandler.handleCheckout(checkout);
    };
    public abstract boolean doHandleCheckout(Checkout checkout);

    public CheckoutHandler setNextHandler(CheckoutHandler checkoutHandler) {
        this.nextHandler = checkoutHandler;
        return this.nextHandler;
    }
}
