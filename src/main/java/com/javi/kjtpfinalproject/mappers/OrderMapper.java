package com.javi.kjtpfinalproject.mappers;

import com.javi.kjtpfinalproject.dto.order.OrderDTO;
import com.javi.kjtpfinalproject.entities.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {
    OrderDTO orderToOrderDto(Order order);
}
