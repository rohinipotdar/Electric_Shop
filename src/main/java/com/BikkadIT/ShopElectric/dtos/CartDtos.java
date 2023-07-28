package com.BikkadIT.ShopElectric.dtos;

import com.BikkadIT.ShopElectric.entities.CartItem;
import com.BikkadIT.ShopElectric.entities.User;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDtos {
    private String cartId;
    private Date createdAt;
    private UserDto userDto;
    private List<CratItemDto> items=new ArrayList<>();
}
