package com.javi.kjtpfinalproject.address.entities;

import com.javi.kjtpfinalproject.customer.entities.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_addresses")
public class OrderAddress extends Address {
//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer;
//
//    @Builder
//    public OrderAddress(UUID addressId, String street, String city, String postalCode, String country, Customer customer) {
//        super(addressId, street, city, postalCode, country);
//        this.customer = customer;
//    }
}
