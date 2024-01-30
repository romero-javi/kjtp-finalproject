package com.javi.kjtpfinalproject.services.impl;

import com.javi.kjtpfinalproject.entities.PaymentMethod;
import com.javi.kjtpfinalproject.repositories.PaymentMethodRepository;
import com.javi.kjtpfinalproject.services.PaymentMethodService;
import com.javi.kjtpfinalproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.javi.kjtpfinalproject.shared.utils.UUIDValidator.isValidUUID;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceJPA implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod findPaymentMethod(String paymentMethodId) {
        isValidUUID(paymentMethodId);

        return paymentMethodRepository.findById(UUID.fromString(paymentMethodId)).orElseThrow(
                () -> new NotFoundException("Payment method with id '%s' not found".formatted(paymentMethodId))
        );
    }

    @Override
    public void savePaymentMethod(PaymentMethod paymentMethod) {
        paymentMethodRepository.save(paymentMethod);
    }
}
