package com.javi.kjtpfinalproject.mappers;

import com.javi.kjtpfinalproject.dto.checkout.CheckoutResponseDTO;
import com.javi.kjtpfinalproject.entities.Checkout;
import org.mapstruct.Mapper;

@Mapper
public interface CheckoutMapper {
    CheckoutResponseDTO checkoutToCheckoutResponseDTO(Checkout checkout);
}
