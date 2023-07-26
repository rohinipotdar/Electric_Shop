package com.BikkadIT.ShopElectric.services.impl;

import com.BikkadIT.ShopElectric.dtos.OrderDtos;
import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.entities.CreateOrderRequest;
import com.BikkadIT.ShopElectric.services.OrderServiceI;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderServiceI {
    @Override
    public OrderDtos createOrder(CreateOrderRequest createOrderRequest) {
        return null;
    }

    @Override
    public Void removeOrder(String orderId) {
        return null;
    }

    @Override
    public List<OrderDtos> getOrderOfUser(String userId) {
        return null;
    }

    @Override
    public PageableResponse<OrderDtos> getOrders(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }
}
