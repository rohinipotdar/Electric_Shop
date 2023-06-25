package com.BikkadIT.ShopElectric.entities;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="category")
public class Category {
    @Id
    private String categoryId;

    private String title;

    private  String description;

    private String coverImage;
}
