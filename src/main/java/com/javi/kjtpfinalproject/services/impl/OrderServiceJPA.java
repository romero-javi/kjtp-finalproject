package com.javi.kjtpfinalproject.services.impl;

import com.javi.kjtpfinalproject.dto.order.OrderDTO;
import com.javi.kjtpfinalproject.entities.Order;
import com.javi.kjtpfinalproject.mappers.OrderMapper;
import com.javi.kjtpfinalproject.repositories.OrderRepository;
import com.javi.kjtpfinalproject.services.OrderService;
import com.javi.kjtpfinalproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.javi.kjtpfinalproject.shared.utils.UUIDValidator.isValidUUID;

@Service
@RequiredArgsConstructor
public class OrderServiceJPA implements OrderService {
    private final OrderRepository orderRepository;
    private OrderMapper orderMapper;

    @Override
    public OrderDTO findOrderById(String orderId) {
        return orderMapper.orderToOrderDto(
                findOrder(orderId)
        );
    }

    @Override
    public Order findOrder(String orderId) {
        isValidUUID(orderId);

        return orderRepository.findById(UUID.fromString(orderId)).orElseThrow(
                () -> new NotFoundException("Order with id '%s' not found".formatted(orderId))
        );
    }

    @Override
    public List<OrderDTO> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::orderToOrderDto)
                .toList();
    }

    @Override
    public OrderDTO saveOrder(Order order) {
        return orderMapper.orderToOrderDto(
                orderRepository.save(order)
        );
    }
}
