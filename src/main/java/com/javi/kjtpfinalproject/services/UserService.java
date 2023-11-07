package com.javi.kjtpfinalproject.services;

import com.javi.kjtpfinalproject.dto.AddressDTO;
import com.javi.kjtpfinalproject.dto.CheckoutResponseDTO;
import com.javi.kjtpfinalproject.dto.UserDTO;
import com.javi.kjtpfinalproject.entities.PaymentMethod;
import com.javi.kjtpfinalproject.entities.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserDTO> findAllUsers();
    UserDTO findUserById(String userId);
    void deleteUserById(String userId);

    void updateUser(User user);

    List<AddressDTO> findUserAddresses(String userId);
    UserDTO saveUserAddress(String userId, AddressDTO newAddress);
    void deleteUserAddress(String userId, String addressId);
    List<PaymentMethod> findUserPaymentMethods(String userId);
    UserDTO saveUserPaymentMethod(String userId, Map<String, Object> paymentMethodData);
    void deleteUserPaymentMethod(String userId, String paymentMethodId);
    CheckoutResponseDTO findUserCheckout(String userId);
    void deleteUserCheckout(String userId);
    User findUser(String userId);
}