package com.javi.kjtpfinalproject.patterns.checkoutchain;

import com.javi.kjtpfinalproject.entities.Checkout;
import com.javi.kjtpfinalproject.shared.exceptions.InsufficientAmountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAvailabilityHandler extends CheckoutHandler {
    @Override
    public boolean doHandleCheckout(Checkout checkout) {
        checkout.getDetails().forEach(
                detail -> {
                    if ((detail.getQuantityToBuy() > detail.getProduct().getQuantity())) {
                        throw new InsufficientAmountException(
                                ("The product with id '%s' does not have enough quantity " +
                                        "available for purchase, the current available quantity is %s")
                                        .formatted(
                                                detail.getProduct().getProductId().toString(),
                                                detail.getProduct().getQuantity()
                                        )
                        );
                    }
                }
        );

        return false;
    }
}
