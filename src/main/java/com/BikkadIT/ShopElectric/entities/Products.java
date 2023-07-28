package com.BikkadIT.ShopElectric.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products_info")
public class Products {
    @Id
    private String productId;

    private String title;
    private String description;
    private Double price;
    private Double discount;
    private int quantity;
    private Date addedDate;
    private Boolean live;
    private Boolean stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id")
    private Category category;

}
