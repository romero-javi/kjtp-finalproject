package com.javi.kjtpfinalproject.patterns.paymentfactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.kjtpfinalproject.entities.CreditCardPayment;
import com.javi.kjtpfinalproject.entities.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CreditCardPaymentFactory implements PaymentMethodFactory {
    private final ObjectMapper objectMapper;

    @Override
    public PaymentMethod createPaymentMethod(Map<String, Object> paymentData) {
        return objectMapper.convertValue(paymentData, CreditCardPayment.class);
    }
}
