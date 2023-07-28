package com.BikkadIT.ShopElectric.entities;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class CreateOrderRequest {
    private String userId;

    private String cartId;
}
