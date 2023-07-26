package com.BikkadIT.ShopElectric.services.impl;

import com.BikkadIT.ShopElectric.dtos.OrderDtos;
import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.entities.CreateOrderRequest;
import com.BikkadIT.ShopElectric.repository.CartRepository;
import com.BikkadIT.ShopElectric.repository.OrderRepository;
import com.BikkadIT.ShopElectric.repository.UserRepository;
import com.BikkadIT.ShopElectric.services.OrderServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderServiceI {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;


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
