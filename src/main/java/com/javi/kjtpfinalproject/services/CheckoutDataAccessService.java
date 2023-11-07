    package com.javi.kjtpfinalproject.services;

    import com.javi.kjtpfinalproject.dto.CheckoutRequestDTO;
    import com.javi.kjtpfinalproject.dto.CheckoutResponseDTO;
    import com.javi.kjtpfinalproject.entities.*;
    import com.javi.kjtpfinalproject.mappers.AddressMapper;
    import com.javi.kjtpfinalproject.mappers.CheckoutMapper;
    import com.javi.kjtpfinalproject.repositories.CheckoutRepository;
    import com.javi.kjtpfinalproject.shared.exceptions.DuplicatedResourceException;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.math.BigDecimal;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.atomic.AtomicReference;

    import static com.javi.kjtpfinalproject.shared.utils.UUIDValidator.isValidUUID;

    @Service
    @RequiredArgsConstructor
    public class CheckoutDataAccessService implements CheckoutService {
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

            isValidUUID(checkoutRequestDTO.addressId());
            isValidUUID(checkoutRequestDTO.paymentMethodId());

            Checkout checkout = new Checkout();
            List<CheckoutDetail> checkoutDetails = new ArrayList<>();
            AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);

            fillCheckoutDetails(checkoutDetails, checkout, checkoutRequestDTO);
            calculateTotal(total, checkoutDetails);

            checkout.setDetails(checkoutDetails);
            checkout.setTotal(total.get());
            checkout.setSelectedAddress(findAddress(checkout, checkoutRequestDTO));
            checkout.setSelectedPaymentMethod(findPaymentMethod(checkout, checkoutRequestDTO));
            checkout.setUser(findUser(userId, checkout));
            checkout.setVersion(1L);

            return checkoutMapper.checkoutToCheckoutResponseDTO(
                    checkoutRepository.save(checkout)
            );
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
