package com.javi.kjtpfinalproject.product.entities;

import com.javi.kjtpfinalproject.checkout.entities.Checkout;
import com.javi.kjtpfinalproject.checkout.entities.CheckoutItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID productId;
    @NotEmpty(message = "Name cannot be null nor blank")
    private String name;
    @NotEmpty(message = "Brand cannot be null nor blank")
    private String brand;
    private Integer availableQuantity;
    private BigDecimal price;
    @OneToOne(mappedBy = "product")
    private CheckoutItem checkoutItem;
}