package com.javi.kjtpfinalproject.customer.service;

import com.javi.kjtpfinalproject.address.dto.AddressResponseDto;
import com.javi.kjtpfinalproject.address.entities.CustomerAddress;
import com.javi.kjtpfinalproject.address.mapper.AddressMapper;
import com.javi.kjtpfinalproject.auth.service.AuthService;
import com.javi.kjtpfinalproject.customer.dto.CustomerRegistrationDto;
import com.javi.kjtpfinalproject.customer.dto.CustomerResponseDto;
import com.javi.kjtpfinalproject.customer.entities.Customer;
import com.javi.kjtpfinalproject.customer.mapper.CustomerMapper;
import com.javi.kjtpfinalproject.customer.repository.CustomerRepository;
import com.javi.kjtpfinalproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.javi.kjtpfinalproject.shared.utils.UUIDValidator.isValidUUID;

@Service
@RequiredArgsConstructor
public class CustomerDataAccessService implements CustomerService {
    private final AuthService authService;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;


    @Override
    public List<CustomerResponseDto> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerResponseDto)
                .toList();
    }

    @Override
    public CustomerResponseDto findCustomerById(String customerId) {
        Customer foundCustomer = findCustomer(customerId);

        return customerMapper.customerToCustomerResponseDto(foundCustomer);
    }

    @Override
    public void deleteCustomerById(String customerId) {
        isValidUUID(customerId);

        authService.deleteUser(customerId);
        customerRepository.deleteById(UUID.fromString(customerId));
    }

    @Override
    public CustomerResponseDto registerCustomer(CustomerRegistrationDto newCustomer) {
        // Create user in Auth Server
        String customerId = authService.createUser(newCustomer);
        isValidUUID(customerId);

        Customer customer = Customer.builder()
                .customerId(UUID.fromString(customerId))
                .firstName(newCustomer.firstName())
                .lastName(newCustomer.lastName())
                .email(newCustomer.email())
                .build();

        List<CustomerAddress> customerAddresses = newCustomer
                .addresses()
                .stream()
                .map(addressMapper::addressRegistrationDtoToAddress)
                .toList();

        customer.setAddresses(customerAddresses);
        customerAddresses.forEach(address -> address.setCustomer(customer));

        // Create user in our API
        return customerMapper.customerToCustomerResponseDto(
                customerRepository.save(customer)
        );
    }

    @Override
    public void updateCustomerProfile(String customerId, CustomerRegistrationDto newData) {
        isValidUUID(customerId);
        authService.updateUser(customerId, newData);

        Customer foundCustomer = findCustomer(customerId);
        foundCustomer.setEmail(newData.email());
        foundCustomer.setFirstName(newData.firstName());
        foundCustomer.setLastName(newData.lastName());
        foundCustomer.setAddresses(
                newData
                        .addresses()
                        .stream()
                        .map(addressMapper::addressRegistrationDtoToAddress)
                        .toList()
        );

        customerRepository.save(foundCustomer);
    }

    @Override
    public List<AddressResponseDto> getCustomerAddresses(String customerId) {
        List<AddressResponseDto> addresses = findCustomer(customerId)
                .getAddresses()
                .stream()
                .map(addressMapper::addressToAddressResponseDto)
                .toList();

        if (addresses.isEmpty()) throw new NotFoundException("You have not set any address yet");

        return addresses;
    }

    private Customer findCustomer(String customerId) {
        isValidUUID(customerId);

        return customerRepository.findById(UUID.fromString(customerId))
                .orElseThrow(
                        () -> new NotFoundException("Customer with id '%s' not found".formatted(customerId))
                );
    }
}
