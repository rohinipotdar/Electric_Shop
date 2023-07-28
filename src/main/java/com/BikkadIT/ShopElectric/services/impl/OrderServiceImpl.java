package com.BikkadIT.ShopElectric.services.impl;

import com.BikkadIT.ShopElectric.dtos.OrderDtos;
import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.dtos.UserDto;
import com.BikkadIT.ShopElectric.entities.CreateOrderRequest;
import com.BikkadIT.ShopElectric.entities.Order;
import com.BikkadIT.ShopElectric.entities.User;
import com.BikkadIT.ShopElectric.exceptions.ResourceNotFoundException;
import com.BikkadIT.ShopElectric.helper.AppConstants;
import com.BikkadIT.ShopElectric.repository.CartRepository;
import com.BikkadIT.ShopElectric.repository.OrderRepository;
import com.BikkadIT.ShopElectric.repository.UserRepository;
import com.BikkadIT.ShopElectric.services.OrderServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void removeOrder(String orderId) {

        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order not found by orderId"));
        this.orderRepository.delete(order);
    }

    @Override
    public List<OrderDtos> getOrderOfUser(String userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        List<Order> orders = this.orderRepository.findByUser(user);

        List<OrderDtos> orderDtos = orders.stream().map(list -> mapper.map(list, OrderDtos.class)).collect(Collectors.toList());

        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDtos> getOrders(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortBy);
        PageRequest pageable = PageRequest.of(pageNo, pageSize,sort);
        Page<Order> page = this.orderRepository.findAll(pageable);

        List<OrderDtos> orderDtosList = page.stream().map(list -> mapper.map(list, OrderDtos.class)).collect(Collectors.toList());

        PageableResponse<OrderDtos> response = new PageableResponse<>();
        response.setContent(orderDtosList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());

        return response;
    }
}
