package com.javi.kjtpfinalproject.services;

import com.javi.kjtpfinalproject.dto.AddressDTO;
import com.javi.kjtpfinalproject.dto.CheckoutResponseDTO;
import com.javi.kjtpfinalproject.dto.UserDTO;
import com.javi.kjtpfinalproject.entities.Checkout;
import com.javi.kjtpfinalproject.entities.PaymentMethod;
import com.javi.kjtpfinalproject.entities.User;
import com.javi.kjtpfinalproject.entities.UserAddress;
import com.javi.kjtpfinalproject.mappers.AddressMapper;
import com.javi.kjtpfinalproject.mappers.CheckoutMapper;
import com.javi.kjtpfinalproject.mappers.UserMapper;
import com.javi.kjtpfinalproject.patterns.factory.PaymentMethodFactory;
import com.javi.kjtpfinalproject.patterns.factory.PaymentMethodFactoryRegistry;
import com.javi.kjtpfinalproject.repositories.UserRepository;
import com.javi.kjtpfinalproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.javi.kjtpfinalproject.shared.utils.UUIDValidator.isValidUUID;

@Service
@RequiredArgsConstructor
public class UserDataAccessService implements UserService {
    private final UserRepository userRepository;
    private final PaymentMethodFactoryRegistry paymentMethodFactoryRegistry;

    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final CheckoutMapper checkoutMapper;

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDto)
                .toList();
    }

    @Override
    public UserDTO findUserById(String userId) {
        return userMapper.userToUserDto(findUser(userId));
    }

    @Override
    public void deleteUserById(String userId) {
        isValidUUID(userId);

        userRepository.deleteById(UUID.fromString(userId));
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<AddressDTO> findUserAddresses(String userId) {
        List<AddressDTO> addresses = findUser(userId)
                .getAddresses()
                .stream()
                .map(addressMapper::userAddressToAddressDTO)
                .toList();

        if (addresses.isEmpty()) throw new NotFoundException("No address set yet");

        return addresses;
    }

    @Override
    public UserDTO saveUserAddress(String userId, AddressDTO address) {
        User user = findUser(userId);

        UserAddress userAddress = addressMapper.addressDtoToUserAddress(address);
        userAddress.setUser(user);
        userAddress.setVersion(1L);

        user.getAddresses().add(userAddress);

        return userMapper.userToUserDto(
                userRepository.save(user)
        );
    }

    @Override
    public void deleteUserAddress(String userId, String addressId) {
        isValidUUID(addressId);

        User user = findUser(userId);
        user.getAddresses().removeIf(
                address -> address.getAddressId().equals(UUID.fromString(addressId))
        );
        userRepository.save(user);
    }

    @Override
    public UserDTO saveUserPaymentMethod(String userId, Map<String, Object> paymentMethodData) {
        User user = findUser(userId);
        PaymentMethod paymentMethod = createPaymentMethod(paymentMethodData);
        paymentMethod.setUser(user);
        paymentMethod.setVersion(1L);

        user.getPaymentMethods().add(paymentMethod);

        return userMapper.userToUserDto(
                userRepository.save(user)
        );
    }

    private PaymentMethod createPaymentMethod(Map<String, Object> paymentMethodData) {
        String paymentType = (String) paymentMethodData.get("paymentType");

        PaymentMethodFactory paymentMethodFactory = paymentMethodFactoryRegistry.getFactory(paymentType);

        return paymentMethodFactory.createPaymentMethod(paymentMethodData);
    }

    @Override
    public List<PaymentMethod> findUserPaymentMethods(String userId) {
        List<PaymentMethod> paymentMethods = findUser(userId).getPaymentMethods();

        if (paymentMethods.isEmpty()) throw new NotFoundException("No payment method set yet");

        return paymentMethods;
    }

    @Override
    public void deleteUserPaymentMethod(String userId, String paymentMethodId) {
        isValidUUID(paymentMethodId);

        User user = findUser(userId);
        user.getPaymentMethods().removeIf(
                paymentMethod -> paymentMethod.getId().equals(UUID.fromString(paymentMethodId))
        );
        userRepository.save(user);
    }

    @Override
    public CheckoutResponseDTO findUserCheckout(String userId) {
        Checkout checkout = findUser(userId).getCheckout();
        if(checkout != null)
            return checkoutMapper.checkoutToCheckoutResponseDTO(checkout);

        throw new NotFoundException("You do not have an active checkout yet.");
    }

    @Override
    public void deleteUserCheckout(String userId) {
        User user = findUser(userId);
        user.setCheckout(null);
        userRepository.save(user);
    }

    public User findUser(String userId) {
        isValidUUID(userId);

        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(
                        () -> new NotFoundException("User with id '%s' not found".formatted(userId))
                );
    }
}
