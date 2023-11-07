package com.javi.kjtpfinalproject.patterns.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.kjtpfinalproject.entities.PaymentMethod;
import com.javi.kjtpfinalproject.entities.PaypalPayment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PayPalPaymentFactory implements PaymentMethodFactory {
    private final ObjectMapper objectMapper;

    @Override
    public PaymentMethod createPaymentMethod(Map<String, Object> paymentData) {
        return objectMapper.convertValue(paymentData, PaypalPayment.class);
    }
}
