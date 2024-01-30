package com.javi.kjtpfinalproject.services;

import com.javi.kjtpfinalproject.dto.address.AddressDTO;
import com.javi.kjtpfinalproject.dto.checkout.CheckoutDetailRequestDTO;
import com.javi.kjtpfinalproject.dto.checkout.CheckoutAddressDTO;
import com.javi.kjtpfinalproject.dto.checkout.CheckoutResponseDTO;
import com.javi.kjtpfinalproject.dto.order.OrderDTO;
import com.javi.kjtpfinalproject.dto.payment.CheckoutPaymentMethodRequestDTO;
import com.javi.kjtpfinalproject.dto.user.UserDTO;
import com.javi.kjtpfinalproject.entities.Checkout;
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

    CheckoutResponseDTO setUserCheckoutAddress(String userId, CheckoutAddressDTO checkoutAddressDTO);

    CheckoutResponseDTO setUserCheckoutPaymentMethod(String userId, CheckoutPaymentMethodRequestDTO checkoutPaymentMethodRequestDTO);

    CheckoutResponseDTO addProductToUserCheckout(String userId, CheckoutDetailRequestDTO checkoutDetailRequestDTO);

    void deleteProductFromUserCheckout(String userId, String productId);

    User findUser(String userId);

    Checkout findUserCheckoutByUser(String userId);
    void saveUser(User user);

    List<OrderDTO> findUserOrders(String userId);
}