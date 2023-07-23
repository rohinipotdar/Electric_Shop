package com.BikkadIT.ShopElectric.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddItemToCartRequest {

    private String productId;
    private int quantity;

}
