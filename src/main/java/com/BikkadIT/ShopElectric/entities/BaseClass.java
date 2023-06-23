package com.BikkadIT.ShopElectric.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "base_Table")
@MappedSuperclass
public class BaseClass implements Serializable {

    @Column(name="is_active")
    private String isActive;

    @CreatedBy
    @Column(name="created_by")
    private String createdBy;

    @Column(name="modified_by")
    @LastModifiedBy
    private  String modifiedBy;

    @Column(name="created_date")
    private String createdDate;

    @UpdateTimestamp
    @Column(name="updated_date")
    private LocalDate updatedDate;
}
