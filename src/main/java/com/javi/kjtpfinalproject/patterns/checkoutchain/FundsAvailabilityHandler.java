package com.javi.kjtpfinalproject.patterns.checkoutchain;

import com.javi.kjtpfinalproject.entities.Checkout;
import com.javi.kjtpfinalproject.shared.exceptions.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FundsAvailabilityHandler extends CheckoutHandler {

    @Override
    public boolean doHandleCheckout(Checkout checkout) {
        BigDecimal funds = checkout.getSelectedPaymentMethod().getDeposit();
        BigDecimal checkoutTotal = checkout.getTotal();
        int comparison = funds.compareTo(checkoutTotal);

        if(comparison < 0) {
            throw new InsufficientFundsException("There is no enough funds to complete the checkout process");
        }

        return false;
    }
}
