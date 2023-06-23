package com.BikkadIT.ShopElectric.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "category_details")

public class Category {
    @Id
    private String categoryId;

    @Column(name = "category_title")
    private String title;

    @Column(name = "category_desc", nullable = false)
    private  String description;

    @Column(name = "cover_image")
    private String coverImage;

}
