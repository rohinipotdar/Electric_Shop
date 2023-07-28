package com.BikkadIT.ShopElectric.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
