package com.BikkadIT.ShopElectric.dtos;

import com.BikkadIT.ShopElectric.entities.Order;
import com.BikkadIT.ShopElectric.entities.Products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderItemDto {
    private int orderItemId;
    private int quantity;
    private int totalPrice;
    private ProductDto productDto;
    private OrderDtos orderDtos;
}
