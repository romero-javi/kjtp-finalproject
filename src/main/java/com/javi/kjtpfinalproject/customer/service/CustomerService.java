package com.javi.kjtpfinalproject.customer.service;

import com.javi.kjtpfinalproject.address.dto.AddressResponseDto;
import com.javi.kjtpfinalproject.customer.dto.CustomerRegistrationDto;
import com.javi.kjtpfinalproject.customer.dto.CustomerResponseDto;
import com.javi.kjtpfinalproject.customer.entities.Customer;

import java.util.List;

public interface CustomerService {
    List<CustomerResponseDto> findAllCustomers();
    CustomerResponseDto findCustomerById(String customerId);
    void deleteCustomerById(String customerId);
    CustomerResponseDto registerCustomer(CustomerRegistrationDto newCustomer);
    void updateCustomerProfile(String customerId, CustomerRegistrationDto newData);

    List<AddressResponseDto> getCustomerAddresses(String customerId);
}