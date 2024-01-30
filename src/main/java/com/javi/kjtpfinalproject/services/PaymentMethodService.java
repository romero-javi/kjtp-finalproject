package com.javi.kjtpfinalproject.services;

import com.javi.kjtpfinalproject.entities.PaymentMethod;

public interface PaymentMethodService {
    PaymentMethod findPaymentMethod(String paymentMethodId);

    void savePaymentMethod(PaymentMethod paymentMethod);
}
