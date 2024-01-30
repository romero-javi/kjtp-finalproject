package com.javi.kjtpfinalproject.services;

import com.javi.kjtpfinalproject.dto.order.OrderDTO;
import com.javi.kjtpfinalproject.entities.Order;

import java.util.List;

public interface OrderService {
    OrderDTO findOrderById(String orderId);
    Order findOrder(String orderId);

    List<OrderDTO> findAllOrders();

    OrderDTO saveOrder(Order order);
}
