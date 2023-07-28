package com.BikkadIT.ShopElectric.entities;

import lombok.*;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
public class Order {

    private String orderId;

    private String orderStatus;

    private String paymentStatus;

    private int orderAmount;

    @Column(length = 1000)
    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderDate;

    private Date DeliveryDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order_item",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItem> orderItems= new ArrayList<>();

}
