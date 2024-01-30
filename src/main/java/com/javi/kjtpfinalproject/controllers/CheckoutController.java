package com.javi.kjtpfinalproject.controllers;

import com.javi.kjtpfinalproject.dto.checkout.CheckoutRequestDTO;
import com.javi.kjtpfinalproject.dto.checkout.CheckoutResponseDTO;
import com.javi.kjtpfinalproject.patterns.checkoutchain.CheckoutChain;
import com.javi.kjtpfinalproject.services.CheckoutService;
import com.javi.kjtpfinalproject.shared.constants.ControllerPaths;
import com.javi.kjtpfinalproject.shared.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final CheckoutChain checkoutChain;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ControllerPaths.CHECKOUT)
    public ResponseEntity<List<CheckoutResponseDTO>> findAllCheckouts() {
        return ResponseEntity.ok(
                checkoutService.findAllCheckouts()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ControllerPaths.CHECKOUT_ID)
    public ResponseEntity<CheckoutResponseDTO> findCheckoutById(@PathVariable("checkoutId") String checkoutId) {
        return ResponseEntity.ok(
                checkoutService.findCheckoutById(checkoutId)
        );
    }

    /*
        Method for users to initialize their checkout
    */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(ControllerPaths.CHECKOUT)
    public ResponseEntity<CheckoutResponseDTO> initCheckout(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody @Valid CheckoutRequestDTO checkout) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        return new ResponseEntity<>(
                checkoutService.initCheckout(userId, checkout),
                HttpStatus.CREATED
        );
    }

    /*
        Method for users to process their checkout, this activates the chain that handles the order,
        delivery and payment process
    */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(ControllerPaths.CURRENT_USER_CHECKOUT_PROCESS)
    public ResponseEntity<Void> processUserCheckout(@RequestHeader("Authorization") String authHeader) {
        String userId = JwtUtils.getUserIdFromJwt(authHeader);

        checkoutChain.processCheckout(userId);
        return ResponseEntity.ok().build();
    }
}
