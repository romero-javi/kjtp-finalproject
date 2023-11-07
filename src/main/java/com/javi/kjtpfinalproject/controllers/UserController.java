package com.javi.kjtpfinalproject.controllers;

import com.javi.kjtpfinalproject.dto.AddressDTO;
import com.javi.kjtpfinalproject.dto.CheckoutResponseDTO;
import com.javi.kjtpfinalproject.dto.UserDTO;
import com.javi.kjtpfinalproject.entities.PaymentMethod;
import com.javi.kjtpfinalproject.services.UserService;
import com.javi.kjtpfinalproject.shared.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    public static final String USERS_PATH = "/api/v1/users";
    public static final String USERS_ID_PATH = USERS_PATH + "/{userId}";
    public static final String ACTUAL_USER_PATH = USERS_PATH + "/me";
    public static final String ACTUAL_USER_ADDRESSES_PATH = ACTUAL_USER_PATH + "/addresses";
    public static final String ACTUAL_USER_ADDRESSES_ID_PATH = ACTUAL_USER_ADDRESSES_PATH + "/{addressId}";
    public static final String ACTUAL_USER_PAYMENT_METHODS_PATH = ACTUAL_USER_PATH + "/payment-methods";
    public static final String ACTUAL_USER_PAYMENT_METHODS_ID_PATH = ACTUAL_USER_PAYMENT_METHODS_PATH + "/{paymentMethodId}";
    public static final String ACTUAL_USER_CHECKOUT_PATH = ACTUAL_USER_PATH + "/checkout";

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(USERS_PATH)
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(USERS_ID_PATH)
    public ResponseEntity<UserDTO> findUserById(@PathVariable("userId") String customerId) {
        return ResponseEntity.ok(userService.findUserById(customerId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(USERS_ID_PATH)
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users to see their profile
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(ACTUAL_USER_PATH )
    public ResponseEntity<UserDTO> findActualUser(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    /*
        Method for users to delete their profile and records
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping(ACTUAL_USER_PATH)
    public ResponseEntity<Void> deleteActualUser(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );

        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users to get their addresses from their profile
     */

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(ACTUAL_USER_ADDRESSES_PATH)
    public ResponseEntity<Map<String, Object>> findUserAddresses(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );

        Map<String, Object> response = new LinkedHashMap<>();
        List<AddressDTO> addresses = userService.findUserAddresses(userId);

        response.put("addresses", addresses);
        return ResponseEntity.ok(response);
    }

    /*
        Method for users to add an address to their profile
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(ACTUAL_USER_ADDRESSES_PATH)
    public ResponseEntity<UserDTO> saveUserAddress(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid AddressDTO newAddress) {
        String userId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );

        UserDTO createdUser = userService.saveUserAddress(userId, newAddress);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /*
        Method for users to delete an address from their profile
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping(ACTUAL_USER_ADDRESSES_ID_PATH)
    public ResponseEntity<Void> deleteUserAddress(@RequestHeader("Authorization") String authHeader, @PathVariable("addressId") String addressId) {
        String userId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );
        userService.deleteUserAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users to get their payment methods from their profile
     */

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(ACTUAL_USER_PAYMENT_METHODS_PATH)
    public ResponseEntity<Map<String, Object>> findUserPaymentMethods(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );

        Map<String, Object> response = new LinkedHashMap<>();
        List<PaymentMethod> paymentMethods = userService.findUserPaymentMethods(userId);

        response.put("paymentMethods", paymentMethods);
        return ResponseEntity.ok(response);
    }

    /*
        Method for users to add payment method to their profile
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(ACTUAL_USER_PAYMENT_METHODS_PATH)
        public ResponseEntity<UserDTO> saveUserPaymentMethod(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid Map<String, Object> paymentMethod) {
        String userId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );

        UserDTO updatedUser = userService.saveUserPaymentMethod(userId, paymentMethod);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    /*
        Method for users to delete payment method from their profile
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping(ACTUAL_USER_PAYMENT_METHODS_ID_PATH)
    public ResponseEntity<Void> deleteUserPaymentMethod(@RequestHeader("Authorization") String authHeader, @PathVariable("paymentMethodId") String paymentMethodId) {
        String userId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );

        userService.deleteUserPaymentMethod(userId, paymentMethodId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users to see their checkout
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(ACTUAL_USER_CHECKOUT_PATH)
    public ResponseEntity<CheckoutResponseDTO> findUserCheckout(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );

        return ResponseEntity.ok(userService.findUserCheckout(userId));
    }

    /*
        Method for users to delete their checkout
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping(ACTUAL_USER_CHECKOUT_PATH)
    public ResponseEntity<Void> deleteUserCheckout(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );

        userService.deleteUserCheckout(userId);
        return ResponseEntity.noContent().build();
    }
}
