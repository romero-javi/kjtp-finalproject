package com.javi.kjtpfinalproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 36, updatable = false, nullable = false)
    private UUID productId;

    private String name;
    private String brand;
    private Integer quantity;
    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    private List<CheckoutDetail> checkoutDetail;
}