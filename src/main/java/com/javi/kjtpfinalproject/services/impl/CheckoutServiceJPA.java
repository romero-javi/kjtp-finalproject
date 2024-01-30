    package com.javi.kjtpfinalproject.services.impl;

    import com.javi.kjtpfinalproject.dto.checkout.CheckoutRequestDTO;
    import com.javi.kjtpfinalproject.dto.checkout.CheckoutResponseDTO;
    import com.javi.kjtpfinalproject.entities.*;
    import com.javi.kjtpfinalproject.mappers.AddressMapper;
    import com.javi.kjtpfinalproject.mappers.CheckoutMapper;
    import com.javi.kjtpfinalproject.repositories.CheckoutRepository;
    import com.javi.kjtpfinalproject.services.*;
    import com.javi.kjtpfinalproject.shared.exceptions.DuplicatedResourceException;
    import com.javi.kjtpfinalproject.shared.exceptions.NotFoundException;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.math.BigDecimal;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.UUID;
    import java.util.concurrent.atomic.AtomicReference;

    import static com.javi.kjtpfinalproject.shared.utils.UUIDValidator.isValidUUID;

    @Service
    @RequiredArgsConstructor
    public class CheckoutServiceJPA implements CheckoutService {
        private final UserService userService;
        private final UserAddressService userAddressService;
        private final ProductService productService;
        private final PaymentMethodService paymentMethodService;

        private final CheckoutRepository checkoutRepository;

        private final AddressMapper addressMapper;
        private final CheckoutMapper checkoutMapper;

        @Override
        @Transactional
        public CheckoutResponseDTO initCheckout(String userId, CheckoutRequestDTO checkoutRequestDTO) {
            validateNotActiveCheckout(userId);

            if(checkoutRequestDTO.addressId() != null)
                isValidUUID(checkoutRequestDTO.addressId());
            if(checkoutRequestDTO.paymentMethodId() != null)
                isValidUUID(checkoutRequestDTO.paymentMethodId());

            Checkout checkout = new Checkout();
            List<CheckoutDetail> checkoutDetails = new ArrayList<>();
            AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);

            fillCheckoutDetails(checkoutDetails, checkout, checkoutRequestDTO);
            calculateTotal(total, checkoutDetails);

            checkout.setDetails(checkoutDetails);
            checkout.setTotal(total.get());

            if(checkoutRequestDTO.addressId() != null)
                checkout.setSelectedAddress(findAddress(checkout, checkoutRequestDTO));
            if(checkoutRequestDTO.paymentMethodId() != null)
                checkout.setSelectedPaymentMethod(findPaymentMethod(checkout, checkoutRequestDTO));

            checkout.setUser(findUser(userId, checkout));
            checkout.setVersion(1L);

            return checkoutMapper.checkoutToCheckoutResponseDTO(
                    checkoutRepository.save(checkout)
            );
        }

        @Override
        public CheckoutResponseDTO findCheckoutById(String checkoutId) {
            Checkout foundCheckout = checkoutRepository.findById(UUID.fromString(checkoutId)).orElseThrow(
                    () -> new NotFoundException("Checkout with id '%s' not found".formatted(checkoutId))
            );
            return checkoutMapper.checkoutToCheckoutResponseDTO(foundCheckout);
        }

        @Override
        public List<CheckoutResponseDTO> findAllCheckouts() {
            return checkoutRepository.findAll()
                    .stream()
                    .map(checkoutMapper::checkoutToCheckoutResponseDTO)
                    .toList();
        }

        private void validateNotActiveCheckout(String userId) {
            User user = userService.findUser(userId);
            if(user.getCheckout() != null)
                throw new DuplicatedResourceException(
                        "You have already an active checkout, complete or delete the active checkout to initiate a new one."
                );
        }

        private PaymentMethod findPaymentMethod(Checkout checkout, CheckoutRequestDTO checkoutRequestDTO) {
            PaymentMethod paymentMethod = paymentMethodService.findPaymentMethod(checkoutRequestDTO.paymentMethodId());
            paymentMethod.setCheckout(checkout);
            return paymentMethod;
        }

        private CheckoutAddress findAddress(Checkout checkout, CheckoutRequestDTO checkoutRequestDTO) {
            isValidUUID(checkoutRequestDTO.addressId());

            UserAddress foundUserAddress = userAddressService.findUserAddress(
                    checkoutRequestDTO.addressId()
            );

            CheckoutAddress checkoutAddress = addressMapper.userAddressToCheckoutAddress(foundUserAddress);
            checkoutAddress.setCheckout(checkout);
            return checkoutAddress;
        }

        private User findUser(String userId, Checkout checkout) {
            User foundUser = userService.findUser(userId);
            foundUser.setCheckout(checkout);
            userService.updateUser(foundUser);
            return foundUser;
        }

        private void fillCheckoutDetails(List<CheckoutDetail> checkoutDetails, Checkout checkout, CheckoutRequestDTO checkoutRequestDTO) {
            checkoutRequestDTO.details().forEach(
                    detail -> {
                        isValidUUID(detail.productId());
                        Product product = productService.findProduct(detail.productId());
                        BigDecimal productPrice = product.getPrice();

                        CheckoutDetail checkoutDetail = CheckoutDetail.builder()
                                .checkout(checkout)
                                .product(product)
                                .quantityToBuy(detail.quantityToBuy())
                                .subtotal(
                                        productPrice.multiply(
                                                BigDecimal.valueOf(detail.quantityToBuy())
                                        )
                                )
                                .build();

                        checkoutDetails.add(checkoutDetail);
                    }
            );
        }

        private void calculateTotal(AtomicReference<BigDecimal> total, List<CheckoutDetail> checkoutDetails) {
            checkoutDetails.forEach(
                    detail -> total.set(total.get().add(detail.getSubtotal()))
            );
        }
    }
