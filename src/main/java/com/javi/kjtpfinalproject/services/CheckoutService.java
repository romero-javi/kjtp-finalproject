package com.javi.kjtpfinalproject.services;

import com.javi.kjtpfinalproject.dto.CheckoutRequestDTO;
import com.javi.kjtpfinalproject.dto.CheckoutResponseDTO;

public interface CheckoutService {
    CheckoutResponseDTO initCheckout(String customerId, CheckoutRequestDTO checkoutRequestDTO);
}
