package com.javi.kjtpfinalproject.patterns.factory;

import com.javi.kjtpfinalproject.shared.exceptions.NotSupportedException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentMethodFactoryRegistry {
    private final Map<String, PaymentMethodFactory> factoryMap = new HashMap<>();

    public PaymentMethodFactoryRegistry(
            CreditCardPaymentFactory creditCardPaymentFactory,
            PayPalPaymentFactory payPalPaymentFactory
    ) {
        factoryMap.put("credit card", creditCardPaymentFactory);
        factoryMap.put("paypal", payPalPaymentFactory);
    }

    public PaymentMethodFactory getFactory(String paymentType) {
        PaymentMethodFactory paymentMethodFactory = factoryMap.get(paymentType);

        if(paymentMethodFactory == null) {
            throw new NotSupportedException("Unsupported payment method, verify the 'paymentType'");
        }

        return paymentMethodFactory;
    }
}
