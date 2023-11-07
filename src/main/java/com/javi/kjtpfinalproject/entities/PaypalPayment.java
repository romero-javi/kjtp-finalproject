package com.javi.kjtpfinalproject.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("paypal")
public class PaypalPayment extends PaymentMethod {
    private String email;
    private BigDecimal deposit;
}
