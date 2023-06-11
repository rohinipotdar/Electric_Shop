package com.BikkadIT.ShopElectric.dtos;
import com.BikkadIT.ShopElectric.validation.ImageNameValid;
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
    @Size(min=4,max = 20, message = "Invalid name..")
    private String name;

    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Invalid email id")
    @Email(message = "email is not according to standard")
    private String email;

    @NotBlank(message = "Enter password")
    private String password;

    @NotEmpty
    private String gender;

    @NotBlank
    @Size(min=10,max=1000, message = "give detail info about user self")
    private String about;

    @ImageNameValid
    private String imageName;
}
