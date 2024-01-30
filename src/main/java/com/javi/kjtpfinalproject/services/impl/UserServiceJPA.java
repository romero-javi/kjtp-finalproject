package com.javi.kjtpfinalproject.services.impl;

import com.javi.kjtpfinalproject.dto.address.AddressDTO;
import com.javi.kjtpfinalproject.dto.checkout.CheckoutDetailRequestDTO;
import com.javi.kjtpfinalproject.dto.checkout.CheckoutAddressDTO;
import com.javi.kjtpfinalproject.dto.checkout.CheckoutResponseDTO;
import com.javi.kjtpfinalproject.dto.order.OrderDTO;
import com.javi.kjtpfinalproject.dto.payment.CheckoutPaymentMethodRequestDTO;
import com.javi.kjtpfinalproject.dto.user.UserDTO;
import com.javi.kjtpfinalproject.entities.*;
import com.javi.kjtpfinalproject.mappers.AddressMapper;
import com.javi.kjtpfinalproject.mappers.CheckoutMapper;
import com.javi.kjtpfinalproject.mappers.OrderMapper;
import com.javi.kjtpfinalproject.mappers.UserMapper;
import com.javi.kjtpfinalproject.patterns.paymentfactory.PaymentMethodFactory;
import com.javi.kjtpfinalproject.patterns.paymentfactory.PaymentMethodFactoryRegistry;
import com.javi.kjtpfinalproject.repositories.UserRepository;
import com.javi.kjtpfinalproject.services.PaymentMethodService;
import com.javi.kjtpfinalproject.services.ProductService;
import com.javi.kjtpfinalproject.services.UserAddressService;
import com.javi.kjtpfinalproject.services.UserService;
import com.javi.kjtpfinalproject.shared.exceptions.InsufficientAmountException;
import com.javi.kjtpfinalproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static com.javi.kjtpfinalproject.shared.utils.UUIDValidator.isValidUUID;

@Service
@RequiredArgsConstructor
public class UserServiceJPA implements UserService {
    private final UserRepository userRepository;

    private final UserAddressService userAddressService;
    private final PaymentMethodService paymentMethodService;
    private final ProductService productService;
    private final PaymentMethodFactoryRegistry paymentMethodFactoryRegistry;

    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final CheckoutMapper checkoutMapper;
    private final OrderMapper orderMapper;

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
        return checkoutMapper.checkoutToCheckoutResponseDTO(
                findUserCheckoutByUser(userId)
        );
    }

    @Override
    public void deleteUserCheckout(String userId) {
        User user = findUser(userId);
        user.setCheckout(null);
        userRepository.save(user);
    }

    @Override
    public CheckoutResponseDTO setUserCheckoutAddress(String userId, CheckoutAddressDTO checkoutAddressDTO) {
        isValidUUID(checkoutAddressDTO.addressId());

        User user = findUser(userId);
        UserAddress userAddress = userAddressService.findUserAddress(checkoutAddressDTO.addressId());

        CheckoutAddress checkoutAddress = addressMapper.userAddressToCheckoutAddress(userAddress);
        checkoutAddress.setCheckout(user.getCheckout());

        user.getCheckout().setSelectedAddress(
                checkoutAddress
        );

        return checkoutMapper.checkoutToCheckoutResponseDTO(
                userRepository.save(user).getCheckout()
        );
    }

    @Override
    public CheckoutResponseDTO setUserCheckoutPaymentMethod(String userId, CheckoutPaymentMethodRequestDTO checkoutPaymentMethodRequestDTO) {
        isValidUUID(checkoutPaymentMethodRequestDTO.paymentMethodId());

        User user = findUser(userId);
        PaymentMethod paymentMethod = paymentMethodService.findPaymentMethod(checkoutPaymentMethodRequestDTO.paymentMethodId());
        paymentMethod.setCheckout(user.getCheckout());

        user.getCheckout().setSelectedPaymentMethod(
                paymentMethod
        );

        return checkoutMapper.checkoutToCheckoutResponseDTO(
                userRepository.save(user).getCheckout()
        );
    }

    @Override
    public CheckoutResponseDTO addProductToUserCheckout(String userId, CheckoutDetailRequestDTO checkoutDetailRequestDTO) {
        isValidUUID(checkoutDetailRequestDTO.productId());

        User user = findUser(userId);
        Product product = productService.findProduct(checkoutDetailRequestDTO.productId());
        Integer productQuantity = product.getQuantity();
        BigDecimal productPrice = product.getPrice();

        if(productQuantity < checkoutDetailRequestDTO.quantityToBuy())
            throw new InsufficientAmountException(
                    "The product with id '%s' does not have enough quantity available for purchase".formatted(product.getProductId())
            );

        CheckoutDetail checkoutDetail = CheckoutDetail.builder()
                .checkout(user.getCheckout())
                .product(product)
                .quantityToBuy(checkoutDetailRequestDTO.quantityToBuy())
                .subtotal(productPrice.multiply(
                        BigDecimal.valueOf(checkoutDetailRequestDTO.quantityToBuy())
                ))
                .build();

        user.getCheckout().getDetails().add(checkoutDetail);
        user.getCheckout().setTotal(
                user.getCheckout().getTotal().add(checkoutDetail.getSubtotal())
        );

        return checkoutMapper.checkoutToCheckoutResponseDTO(
                userRepository.save(user).getCheckout()
        );
    }

    @Override
    public void deleteProductFromUserCheckout(String userId, String productId) {
        isValidUUID(productId);

        User user = findUser(userId);
        user.getCheckout().getDetails().removeIf(
                detail -> detail
                        .getProduct()
                        .getProductId()
                        .equals(UUID.fromString(productId))
        );

        AtomicReference<BigDecimal> totalRef = new AtomicReference<>(BigDecimal.ZERO);

        user.getCheckout().getDetails().forEach(
                detail -> totalRef.set(
                        totalRef.get().add(detail.getSubtotal())
                )
        );

        user.getCheckout().setTotal(totalRef.get());
        userRepository.save(user).getCheckout();
    }
    @Override
    public Checkout findUserCheckoutByUser(String userId) {
        Checkout checkout = findUser(userId).getCheckout();
        if (checkout == null)
            throw new NotFoundException("You do not have an active checkout yet.");

        return checkout;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<OrderDTO> findUserOrders(String userId) {
        return findUser(userId).getOrders().stream().map(orderMapper::orderToOrderDto).toList();
    }

    public User findUser(String userId) {
        isValidUUID(userId);

        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(
                        () -> new NotFoundException("User with id '%s' not found".formatted(userId))
                );
    }
}
