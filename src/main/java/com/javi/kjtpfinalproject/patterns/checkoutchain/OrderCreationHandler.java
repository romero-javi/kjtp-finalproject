
package com.javi.kjtpfinalproject.patterns.checkoutchain;

import com.javi.kjtpfinalproject.entities.Checkout;
import com.javi.kjtpfinalproject.entities.Order;
import com.javi.kjtpfinalproject.entities.OrderDetail;
import com.javi.kjtpfinalproject.entities.User;
import com.javi.kjtpfinalproject.mappers.AddressMapper;
import com.javi.kjtpfinalproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrderCreationHandler extends CheckoutHandler {
    private final UserService userService;
    private final AddressMapper addressMapper;

    @Override
    public boolean doHandleCheckout(Checkout checkout) {
        User user = userService.findUser(checkout.getUser().getUserId().toString());

        Order order = new Order();
        List<OrderDetail> orderDetails = new ArrayList<>();
        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);

        fillOrderDetails(orderDetails, order, checkout);
        calculateTotal(total, orderDetails);

        order.setDetails(orderDetails);
        order.setTotal(total.get());
        order.setSelectedAddress(
                addressMapper.checkoutAddressToOrderAddress(checkout.getSelectedAddress())
        );
        order.setSelectedPaymentMethod(
                checkout.getSelectedPaymentMethod()
        );
        order.setUser(user);
        order.setVersion(1L);

        user.getOrders().add(order);
        userService.saveUser(user);

        return false;
    }

    private void calculateTotal(AtomicReference<BigDecimal> total, List<OrderDetail> orderDetails) {
        orderDetails.forEach(
                detail -> total.set(total.get().add(detail.getSubtotal()))
        );
    }

    private void fillOrderDetails(List<OrderDetail> orderDetails, Order order, Checkout checkout) {
        checkout.getDetails().forEach(
                detail -> {
                    OrderDetail orderDetail = OrderDetail.builder()
                            .order(order)
                            .quantityToBuy(detail.getQuantityToBuy())
                            .product(detail.getProduct())
                            .subtotal(detail.getSubtotal())
                            .version(1L)
                            .build();

                    orderDetails.add(orderDetail);
                }
        );
    }
}
