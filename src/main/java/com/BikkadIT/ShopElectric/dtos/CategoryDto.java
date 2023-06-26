package com.BikkadIT.ShopElectric.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @Size(min = 5, max = 50)
    @NotEmpty(message = "title should not Empty")
    private String title;

    @NotNull(message = " Give some description about title ")
    @Size(min=5, max=500)
    private  String description;

    private String coverImage;
}