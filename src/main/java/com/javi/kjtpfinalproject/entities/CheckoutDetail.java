package com.javi.kjtpfinalproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class CheckoutDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 36, updatable = false, nullable = false)
    private UUID checkoutDetailId;

    @ManyToOne
    @JoinColumn(name = "checkout_id")
    private Checkout checkout;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantityToBuy;
    private BigDecimal subtotal;
}
