package com.javi.kjtpfinalproject.customer.controller;

import com.javi.kjtpfinalproject.address.dto.AddressResponseDto;
import com.javi.kjtpfinalproject.customer.dto.CustomerRegistrationDto;
import com.javi.kjtpfinalproject.customer.dto.CustomerResponseDto;
import com.javi.kjtpfinalproject.customer.entities.Customer;
import com.javi.kjtpfinalproject.customer.service.CustomerService;
import com.javi.kjtpfinalproject.shared.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    static final String BASE_URI = "/api/v1/customers";
    private final CustomerService customerService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(BASE_URI)
    public ResponseEntity<List<CustomerResponseDto>> findAllCustomers() {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(BASE_URI + "/{customerId}")
    public ResponseEntity<CustomerResponseDto> findCustomerById(@PathVariable("customerId") String customerId) {
        return ResponseEntity.ok(customerService.findCustomerById(customerId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(BASE_URI + "/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable String customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for new users to register
        THIS SHOULD BE THE ONLY PUBLIC ENDPOINT IN THE API
     */
    @PostMapping(BASE_URI)
    public ResponseEntity<CustomerResponseDto> registerCustomer(@RequestBody @Valid CustomerRegistrationDto customer) throws URISyntaxException {
        CustomerResponseDto createdCustomer = customerService.registerCustomer(customer);
        String location = BASE_URI.concat("/%s/profile".formatted(createdCustomer.email()));

        return ResponseEntity.created(new URI(location)).body(createdCustomer);
    }

    /*
        Method for users to see their profile
     */
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping(BASE_URI + "/myProfile")
    public ResponseEntity<CustomerResponseDto> getCustomerProfile(@RequestHeader("Authorization") String authHeader) {
        String customerId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );
        return ResponseEntity.ok(customerService.findCustomerById(customerId));
    }


    /*
        Method for users to update their profile
     */
    @PreAuthorize("hasRole('USER')")
    @PutMapping(BASE_URI + "/myProfile")
    public ResponseEntity<Void> updateCustomerProfile(@RequestHeader("Authorization") String authHeader, @RequestBody CustomerRegistrationDto newData) {
        String customerId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );
        customerService.updateCustomerProfile(customerId, newData);

        return ResponseEntity.noContent().build();
    }

    /*
        Method for users to delete their profile and records from the app
     */
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(BASE_URI + "/myProfile")
    public ResponseEntity<Void> deleteCustomerProfile(@RequestHeader("Authorization") String authHeader) {
        String customerId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );

        customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users to get their addresses to their profile
     */

    @PreAuthorize("hasRole('USER')")
    @GetMapping(BASE_URI + "/myProfile/addresses")
    public ResponseEntity<Map<String, Object>> getCustomerAddresses(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> response = new LinkedHashMap<>();

        String customerId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );

        List<AddressResponseDto> addresses = customerService.getCustomerAddresses(customerId);

        response.put("addresses", addresses);
        return ResponseEntity.ok(response);
    }

}
