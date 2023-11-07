package com.javi.kjtpfinalproject.patterns.factory;

import com.javi.kjtpfinalproject.entities.PaymentMethod;

import java.util.Map;

public interface PaymentMethodFactory {
    PaymentMethod createPaymentMethod(Map<String, Object> paymentData);
}
