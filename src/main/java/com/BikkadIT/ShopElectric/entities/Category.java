package com.BikkadIT.ShopElectric.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Products> products=new ArrayList<>();
}
