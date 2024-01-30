package com.javi.kjtpfinalproject.mappers;

import com.javi.kjtpfinalproject.dto.address.AddressDTO;
import com.javi.kjtpfinalproject.entities.CheckoutAddress;
import com.javi.kjtpfinalproject.entities.OrderAddress;
import com.javi.kjtpfinalproject.entities.UserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressMapper {
    @Mapping(target = "user", ignore = true)
    UserAddress addressDtoToUserAddress(AddressDTO addressDto);
    AddressDTO userAddressToAddressDTO(UserAddress userAddress);

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "checkout", ignore = true)
    CheckoutAddress userAddressToCheckoutAddress(UserAddress userAddress);

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderAddress checkoutAddressToOrderAddress(CheckoutAddress checkoutAddress);
}
