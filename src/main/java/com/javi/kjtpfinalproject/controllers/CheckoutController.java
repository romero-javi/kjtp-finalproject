package com.javi.kjtpfinalproject.controllers;

import com.javi.kjtpfinalproject.dto.CheckoutRequestDTO;
import com.javi.kjtpfinalproject.dto.CheckoutResponseDTO;
import com.javi.kjtpfinalproject.services.CheckoutService;
import com.javi.kjtpfinalproject.shared.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CheckoutController {
    static final String CHECKOUT_PATH = "/api/v1/checkout";
    private final CheckoutService checkoutService;

    /*
        Method for users to initialize their checkout
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(CHECKOUT_PATH)
    public ResponseEntity<CheckoutResponseDTO> initCheckout(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid CheckoutRequestDTO checkout) {
        String userId = JwtUtils.extractSubjectClaim(
                JwtUtils.extractJwt(authHeader)
        );

        CheckoutResponseDTO createCheckout = checkoutService.initCheckout(userId, checkout);
        return new ResponseEntity<>(createCheckout, HttpStatus.CREATED);
    }
}
