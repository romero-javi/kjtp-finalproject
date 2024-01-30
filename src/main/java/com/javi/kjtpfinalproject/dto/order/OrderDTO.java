package com.javi.kjtpfinalproject.dto.order;

import com.javi.kjtpfinalproject.dto.address.AddressDTO;
import com.javi.kjtpfinalproject.dto.user.UserLessDataDTO;
import com.javi.kjtpfinalproject.entities.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderDTO(
        UUID orderId,
        BigDecimal total,
        List<OrderDetailDTO> details,
        UserLessDataDTO user,
        PaymentMethod selectedPaymentMethod,
        AddressDTO selectedAddress
) {
}
