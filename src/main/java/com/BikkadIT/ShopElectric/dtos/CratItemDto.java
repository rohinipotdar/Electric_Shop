package com.BikkadIT.ShopElectric.dtos;

import com.BikkadIT.ShopElectric.entities.Cart;
import com.BikkadIT.ShopElectric.entities.Products;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CratItemDto {
    private int cateItemId;
    private ProductDto productDto;
    private int quantity;
    private int totalPrice;
}
