package com.javi.kjtpfinalproject.controllers;

import com.javi.kjtpfinalproject.dto.address.AddressDTO;
import com.javi.kjtpfinalproject.dto.checkout.CheckoutDetailRequestDTO;
import com.javi.kjtpfinalproject.dto.checkout.CheckoutAddressDTO;
import com.javi.kjtpfinalproject.dto.checkout.CheckoutResponseDTO;
import com.javi.kjtpfinalproject.dto.order.OrderDTO;
import com.javi.kjtpfinalproject.dto.payment.CheckoutPaymentMethodRequestDTO;
import com.javi.kjtpfinalproject.dto.user.UserDTO;
import com.javi.kjtpfinalproject.services.UserService;
import com.javi.kjtpfinalproject.shared.constants.ControllerPaths;
import com.javi.kjtpfinalproject.shared.utils.JwtUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
    private final UserService userService;

    /*
        Method for admin to see all users
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ControllerPaths.USERS)
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return ResponseEntity.ok(
                userService.findAllUsers()
        );
    }

    /*
        Method for admin to find user by id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ControllerPaths.USER_ID)
    public ResponseEntity<UserDTO> findUserById(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(
                userService.findUserById(userId)
        );
    }


    /*
        Method for admin to delete a user by id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(ControllerPaths.USER_ID)
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users to see their own profile
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(ControllerPaths.CURRENT_USER)
    public ResponseEntity<UserDTO> findCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        return ResponseEntity.ok(
                userService.findUserById(userId)
        );
    }

    /*
        Method for users to delete their own profile
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping(ControllerPaths.CURRENT_USER)
    public ResponseEntity<Void> deleteCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        userService.findUserById(userId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users to get their addresses from their profile
     */

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(ControllerPaths.CURRENT_USER_ADDRESSES)
    public ResponseEntity<Map<String, Object>> findUserAddresses(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("addresses", userService.findUserAddresses(userId));

        return ResponseEntity.ok(response);
    }

    /*
        Method for users to add an address to their profile
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(ControllerPaths.CURRENT_USER_ADDRESSES)
    public ResponseEntity<UserDTO> saveUserAddress(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid AddressDTO newAddress) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        UserDTO newData = userService.saveUserAddress(userId, newAddress);
        return new ResponseEntity<>(newData, HttpStatus.CREATED);
    }

    /*
        Method for users to delete an address from their profile
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping(ControllerPaths.CURRENT_USER_ADDRESS_ID)
    public ResponseEntity<Void> deleteUserAddress(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("addressId") String addressId) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        userService.deleteUserAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users to get their payment methods from their profile
     */

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(ControllerPaths.CURRENT_USER_PAYMENT_METHODS)
    public ResponseEntity<Map<String, Object>> findUserPaymentMethods(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("paymentMethods", userService.findUserPaymentMethods(userId));

        return ResponseEntity.ok(response);
    }

    /*
        Method for users to add payment method to their profile
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(ControllerPaths.CURRENT_USER_PAYMENT_METHODS)
        public ResponseEntity<UserDTO> saveUserPaymentMethod(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid Map<String, Object> paymentMethod) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        UserDTO updatedUser = userService.saveUserPaymentMethod(userId, paymentMethod);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    /*
        Method for users to delete payment method from their profile
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping(ControllerPaths.CURRENT_USER_PAYMENT_METHOD_ID)
    public ResponseEntity<Void> deleteUserPaymentMethod(@RequestHeader("Authorization") String authHeader, @PathVariable("paymentMethodId") String paymentMethodId) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        userService.deleteUserPaymentMethod(userId, paymentMethodId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users to see their checkout
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(ControllerPaths.CURRENT_USER_CHECKOUT)
    public ResponseEntity<CheckoutResponseDTO> findUserCheckout(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        return ResponseEntity.ok(
                userService.findUserCheckout(userId)
        );
    }

    /*
        Method for users to see their checkout
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(ControllerPaths.CURRENT_USER_ORDERS)
    public ResponseEntity<List<OrderDTO>> findUserOrders(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        return ResponseEntity.ok(
                userService.findUserOrders(userId)
        );
    }

    /*
        Method for users to set an address to their checkout
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(ControllerPaths.CURRENT_USER_CHECKOUT_ADDRESS)
    public ResponseEntity<CheckoutResponseDTO> setUserCheckoutAddress(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid CheckoutAddressDTO checkoutAddressDTO) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        return ResponseEntity.ok(
                userService.setUserCheckoutAddress(userId, checkoutAddressDTO)
        );
    }

    /*
        Method for users to set a payment method to their checkout
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(ControllerPaths.CURRENT_USER_CHECKOUT_PAYMENT_METHOD)
    public ResponseEntity<CheckoutResponseDTO> setUserCheckoutPaymentMethod(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid CheckoutPaymentMethodRequestDTO checkoutPaymentMethodRequestDTO) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        return ResponseEntity.ok(
                userService.setUserCheckoutPaymentMethod(userId, checkoutPaymentMethodRequestDTO)
        );
    }

    /*
        Method for users to add a product to their checkout
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(ControllerPaths.CURRENT_USER_CHECKOUT_PRODUCT)
    public ResponseEntity<CheckoutResponseDTO> addProductToUserCheckout(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid CheckoutDetailRequestDTO checkoutDetailRequestDTO) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        return ResponseEntity.ok(
                userService.addProductToUserCheckout(userId, checkoutDetailRequestDTO)
        );
    }

    /*
        Method for users to delete a product from their checkout
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping(ControllerPaths.CURRENT_USER_CHECKOUT_PRODUCT_ID)
    public ResponseEntity<CheckoutResponseDTO> deleteProductFromUserCheckout(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("productId")
            @NotEmpty(message = "You must specify the UUID of the product to delete in the as a path variable")
            String productId
    ) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        userService.deleteProductFromUserCheckout(userId, productId);
        return ResponseEntity.noContent().build();
    }

    /*
        Method for users to delete their checkout
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping(ControllerPaths.CURRENT_USER_CHECKOUT)
    public ResponseEntity<Void> deleteUserCheckout(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        userService.deleteUserCheckout(userId);
        return ResponseEntity.noContent().build();
    }
}
