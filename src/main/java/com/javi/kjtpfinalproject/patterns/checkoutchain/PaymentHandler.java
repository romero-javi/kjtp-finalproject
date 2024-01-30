
package com.javi.kjtpfinalproject.patterns.checkoutchain;

import com.javi.kjtpfinalproject.entities.Checkout;
import com.javi.kjtpfinalproject.entities.PaymentMethod;
import com.javi.kjtpfinalproject.services.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentHandler extends CheckoutHandler {
    private final PaymentMethodService paymentMethodService;
    @Override
    public boolean doHandleCheckout(Checkout checkout) {
        PaymentMethod paymentMethod = checkout.getSelectedPaymentMethod();

        paymentMethod.setDeposit(
                paymentMethod.getDeposit().subtract(checkout.getTotal())
        );

        paymentMethodService.savePaymentMethod(paymentMethod);

        return false;
    }
}
