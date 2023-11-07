package com.javi.kjtpfinalproject.address.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(Types.VARCHAR)
    private UUID addressId;
    @NotEmpty(message = "Street cannot be null nor blank")
    private String street;
    @NotEmpty(message = "City cannot be null nor blank")
    private String city;
    @NotEmpty(message = "Postal code cannot be null nor blank")
    private String postalCode;
    @NotEmpty(message = "Country cannot be null nor blank")
    private String country;

    public Address(UUID addressId, String street, String city, String postalCode, String country) {
        this.addressId = addressId;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }
}
