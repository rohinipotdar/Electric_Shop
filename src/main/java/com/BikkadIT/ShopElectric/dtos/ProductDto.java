package com.BikkadIT.ShopElectric.dtos;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private String productId;

    @NotEmpty
    @Size(min=5,max=50,message = "title size should be in between 5 to 50 chars")
    private String title;

    @NotEmpty
    @Size(min=5,max=500,message = "description size should be in between 5 to 50 chars")
    private String description;

    @NotEmpty(message = "title size should be in between 5 to 50 chars")
    private Double price;

    @Range(min=5, max = 50, message = "Discount should not be more than 50%")
    private Double discount;

    @Range(min=1, message = "quantity should more than 1")
    private int quantity;

    private Data addedDate;

    private Boolean live;

    private Boolean stock;
}
