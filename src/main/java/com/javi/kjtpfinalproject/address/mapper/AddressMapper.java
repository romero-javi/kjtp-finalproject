package com.javi.kjtpfinalproject.address.mapper;

import com.javi.kjtpfinalproject.address.dto.AddressRegistrationDto;
import com.javi.kjtpfinalproject.address.dto.AddressResponseDto;
import com.javi.kjtpfinalproject.address.entities.CheckoutAddress;
import com.javi.kjtpfinalproject.address.entities.CustomerAddress;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
    CustomerAddress addressRegistrationDtoToAddress(AddressRegistrationDto addressRegistrationDto);
    AddressResponseDto addressToAddressResponseDto(CustomerAddress customerAddress);

    CheckoutAddress addressRegistrationDtoToCheckoutAddress(AddressRegistrationDto addressRegistrationDto);
    AddressResponseDto addressToAddressResponseDto(CheckoutAddress customerAddress);
}
