package com.javi.kjtpfinalproject.address.entities;

import com.javi.kjtpfinalproject.customer.entities.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "checkout_addresses")
public class CheckoutAddress extends Address {


//    @Builder
//    public CheckoutAddress(UUID addressId, String street, String city, String postalCode, String country, Customer customer) {
//        super(addressId, street, city, postalCode, country);
//        this.customer = customer;
//    }
}
