package com.BikkadIT.ShopElectric.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="category_details")
public class Category {
    @Id
    private String categoryId;

    @Size(min = 5, max = 50)
    @NotEmpty(message = "title should not Empty")
    private String title;

    @NotNull(message = " Give some description about title ")
    @Size(min=5, max=500)
    private  String description;

    private String coverImage;
}
