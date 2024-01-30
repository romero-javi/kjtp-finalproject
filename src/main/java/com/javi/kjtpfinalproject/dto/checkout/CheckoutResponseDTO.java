package com.javi.kjtpfinalproject.dto.checkout;

import com.javi.kjtpfinalproject.dto.address.AddressDTO;
import com.javi.kjtpfinalproject.dto.user.UserLessDataDTO;
import com.javi.kjtpfinalproject.entities.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CheckoutResponseDTO(
        UUID checkoutId,
        BigDecimal total,
        List<CheckoutDetailResponseDTO> details,
        UserLessDataDTO user,
        PaymentMethod selectedPaymentMethod,
        AddressDTO selectedAddress
) {
}
