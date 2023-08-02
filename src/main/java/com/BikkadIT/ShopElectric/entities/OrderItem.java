package com.BikkadIT.ShopElectric.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "order_item")
@Builder
public class OrderItem extends BaseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    private int quantity;

    private int totalPrice;

    @OneToOne
    @JoinColumn(name="product_id")
    private Products products;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
