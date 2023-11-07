package com.javi.kjtpfinalproject.customer.mapper;

import com.javi.kjtpfinalproject.customer.dto.CustomerRegistrationDto;
import com.javi.kjtpfinalproject.customer.dto.CustomerResponseDto;
import com.javi.kjtpfinalproject.customer.entities.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerResponseDto customerToCustomerResponseDto(Customer customer);
}
