package com.BikkadIT.ShopElectric.services.impl;

import com.BikkadIT.ShopElectric.dtos.CreateOrderRequest;
import com.BikkadIT.ShopElectric.dtos.OrderDtos;
import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.dtos.UserDto;
import com.BikkadIT.ShopElectric.entities.*;
import com.BikkadIT.ShopElectric.exceptions.BadApiRequestException;
import com.BikkadIT.ShopElectric.exceptions.ResourceNotFoundException;
import com.BikkadIT.ShopElectric.helper.AppConstants;
import com.BikkadIT.ShopElectric.repository.CartRepository;
import com.BikkadIT.ShopElectric.repository.OrderRepository;
import com.BikkadIT.ShopElectric.repository.UserRepository;
import com.BikkadIT.ShopElectric.services.OrderServiceI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
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

    private static Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public OrderDtos createOrder(CreateOrderRequest orderDto) {
        logger.info("Dao Request initialized to create order ");
        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+userId));
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart not found"));

        //fetch CartItems
        List<CartItem> cartitems = cart.getItems();
        logger.info("No. of cartItems: ",cartitems.size());
        if(cartitems.size() <= 0){
            throw new BadApiRequestException("Items invalid");
        }

        // Generate Order
        Order order = Order.builder()
                .orderId(UUID.randomUUID().toString())
                .billingAddress(orderDto.getBillingAddress())
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .orderDate(new Date())
                .deliveryDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .user(user)
                .build();

        // Convert CartItems to OrderItems

        AtomicReference<Integer> orderAmount=new  AtomicReference<Integer>(0);
        List<OrderItem> orderItems = cartitems.stream().map(cartitem -> {

            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartitem.getQuantity())
                    .products(cartitem.getProducts())
                    .totalPrice((int) (cartitem.getQuantity() * cartitem.getProducts().getDiscount()))
                    .order(order)
                    .build();
            orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());
            orderItem.setCreatedBy(user.getCreatedBy());
            orderItem.setModifiedDate(user.getModifiedDate());
            orderItem.setIsActive(user.getIsActive());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());
        order.setCreatedBy(user.getCreatedBy());
        order.setModifiedBy(user.getModifiedBy());
        order.setIsActive(user.getIsActive());

        // After converting into OrderItems, clear Cart & save it
        cart.getItems().clear();
        cartRepository.save(cart);

        //save order
        Order saveOrder = orderRepository.save(order);
        logger.info("Dao Request completed to create order ");
        return mapper.map(saveOrder,OrderDtos.class);
    }
    /*
     * @author: rohini
     * @implNote:  This method is for remove order by orderId
     * @param: orderId
     */
    @Override
    public void removeOrder(String orderId) {
        logger.info("Initiating Dao call for remove order by orderId : {}", orderId);
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order not found by orderId"));
        logger.info("complete Dao call for remove order by orderId : {}", orderId);
        this.orderRepository.delete(order);
    }

    /*
     * @author: rohini
     * @implNote:  This method is for get order by userId
     * @param: userId
     * @return
     */
    @Override
    public List<OrderDtos> getOrderOfUser(String userId) {
        logger.info("Initiating Dao call for get order by userId : {}", userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));

        List<Order> orders = this.orderRepository.findByUser(user);

        List<OrderDtos> orderDtos = orders.stream().map(list -> mapper.map(list, OrderDtos.class)).collect(Collectors.toList());
        logger.info("complete Dao call for get order by userId : {}", userId);
        return orderDtos;
    }

    /*
     * @author: rohini
     * @implNote:  This method is for get All orders
     * @param: pageNo, pageSize, sortBy, sortDir
     * @return
     */
    @Override
    public PageableResponse<OrderDtos> getOrders(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Initiating Dao call for get all orders ");
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
        logger.info("complete Dao call for get all orders ");
        return response;
    }
}
