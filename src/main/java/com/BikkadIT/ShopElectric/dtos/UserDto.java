package com.BikkadIT.ShopElectric.dtos;
import lombok.*;

import javax.validation.constraints.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @NotEmpty
    @Size(min=4,message = "User name min 4 characters..")
    private String name;

    @Email(message = "email is not according to standard")
    private String email;

    @NotBlank
    private String password;

    @NotEmpty
    private String gender;

    @NotBlank
    @Size(min=10,max=1000, message = "give detail info about user")
    private String about;

    private String imageName;
}
