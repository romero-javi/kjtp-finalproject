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
@DiscriminatorValue("credit card")
public class CreditCardPayment extends PaymentMethod {
    private String cardType;
    private String cardNumber;
    private Integer expireMonth;
    private Integer expireYear;
    private String cardHolderName;
    private BigDecimal deposit;
}
