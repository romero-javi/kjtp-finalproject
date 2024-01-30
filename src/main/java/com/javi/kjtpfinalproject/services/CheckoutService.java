package com.javi.kjtpfinalproject.services;

import com.javi.kjtpfinalproject.dto.checkout.CheckoutRequestDTO;
import com.javi.kjtpfinalproject.dto.checkout.CheckoutResponseDTO;

import java.util.List;

public interface CheckoutService {
    CheckoutResponseDTO initCheckout(String customerId, CheckoutRequestDTO checkoutRequestDTO);

    CheckoutResponseDTO findCheckoutById(String checkoutId);

    List<CheckoutResponseDTO> findAllCheckouts();
}
