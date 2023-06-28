package com.BikkadIT.ShopElectric.services.impl;

import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.dtos.UserDto;
import com.BikkadIT.ShopElectric.entities.User;
import com.BikkadIT.ShopElectric.repository.UserRepository;
import com.BikkadIT.ShopElectric.services.UserServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private ModelMapper mapper;

    User user;

    User user1;

    List<User> users;

    UserDto userDto;

    @BeforeEach
    public void init(){

        userDto = UserDto.builder().
        userId("101")
                .name("raj")
                .email("raj@gmail.com")
                .gender("male")
                .password("raj123")
                .about("java developer")
                .imageName("raj.png")
                .build();

        user = User.builder().
                userId("102")
                .name("rani")
                .email("rani@gmail.com")
                .gender("female")
                .password("rani123")
                .about("java developer")
                .imageName("rani.png")
                .build();

        user1 = User.builder().
                userId("103")
                .name("aditya")
                .email("aditya@gmail.com")
                .gender("male")
                .password("aditya123")
                .about("java developer")
                .imageName("aditya.png")
                .build();

        users = new ArrayList<>();
        users.add(user);
        users.add(user1);

    }

    @Test
    void createUser() {

        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(user);

        UserDto userDto1=userServiceI.createUser(userDto);

        Assertions.assertEquals("rani",user.getName());
    }

    @Test
    void getUserById() {
        Mockito.when(this.userRepository.findById(user.getUserId())).thenReturn(Optional.ofNullable(user));

        UserDto user2 = userServiceI.getUserById(user.getUserId());

        Assertions.assertEquals("102",user2.getUserId());

    }

    @Test
    void getUserByEmailAndPassword() {

        Mockito.when(this.userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword())).thenReturn(Optional.ofNullable(user));

        UserDto user2 = userServiceI.getUserByEmailAndPassword(user.getEmail(), user.getPassword());

        Assertions.assertEquals("rani@gmail.com",user.getEmail());
    }

    @Test
    void getAllUsers() {
        Mockito.when(this.userRepository.findAll()).thenReturn(users);

        PageableResponse<UserDto> allUsers = userServiceI.getAllUsers(0, 2, "userId", "ASC");
        Assertions.assertEquals(users,allUsers);
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}