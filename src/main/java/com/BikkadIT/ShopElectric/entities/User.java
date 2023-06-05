package com.BikkadIT.ShopElectric.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
@Builder
public class User {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    @Column(name="user_name")
    private String name;

    @Column(name="user_email",unique = true)
       private String email;

    @Column(name = "password")
    private String password;

    @Column(name="gender")
    private String gender;

    @Column(name="about", length = 1000)
    private String about;

    @Column(name = "image_name")
    private String imageName;
}
