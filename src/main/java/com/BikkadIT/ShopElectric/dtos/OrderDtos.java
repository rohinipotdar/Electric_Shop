package com.BikkadIT.ShopElectric.dtos;

import com.BikkadIT.ShopElectric.entities.OrderItem;
import com.BikkadIT.ShopElectric.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDtos {

    private String orderId;
    private String orderStatus;
    private String paymentStatus;
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate;
    private Date DeliveryDate;
    private UserDto userDto;
    private List<OrderItemDto> orderItemsDtos = new ArrayList<>();
}
