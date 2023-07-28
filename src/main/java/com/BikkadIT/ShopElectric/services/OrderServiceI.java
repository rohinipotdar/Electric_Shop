package com.BikkadIT.ShopElectric.services;

import com.BikkadIT.ShopElectric.dtos.OrderDtos;
import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.entities.CreateOrderRequest;

import java.util.List;

public interface OrderServiceI {

    OrderDtos createOrder(OrderDtos orderDtos);

    void removeOrder(String orderId);

    List<OrderDtos> getOrderOfUser(String userId);

    PageableResponse<OrderDtos> getOrders(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

}
