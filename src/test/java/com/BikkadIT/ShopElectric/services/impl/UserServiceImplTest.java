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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
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

                user = User.builder().
                userId("101")
                .name("rani")
                .email("rani@gmail.com")
                .gender("female")
                .password("rani123")
                .about("java developer")
                .imageName("rani.png")
                .build();

        user1 = User.builder().
                userId("102")
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
    void createUserTest() {

        Mockito.when(this.userRepository.save(Mockito.any())).thenReturn(user);

        UserDto userDto1=userServiceI.createUser(userDto);

        Assertions.assertEquals("rani",user.getName());
    }

    @Test
    void getUserByIdTest() {
        Mockito.when(this.userRepository.findById(user.getUserId())).thenReturn(Optional.ofNullable(user));

        UserDto user2 = userServiceI.getUserById(user.getUserId());

        Assertions.assertEquals("102",user2.getUserId());

    }

    @Test
    void getUserByEmailAndPasswordTest() {

        Mockito.when(this.userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword())).thenReturn(Optional.ofNullable(user));

        UserDto user2 = userServiceI.getUserByEmailAndPassword(user.getEmail(), user.getPassword());

        Assertions.assertEquals("rani@gmail.com",user.getEmail());
    }

    @Test
    public void getAllUsersTest() {

        users= Arrays.asList(user,user1);

        Page<User> pages=new PageImpl<>(users);

        Mockito.when(this.userRepository.findAll((Pageable)Mockito.any())).thenReturn(pages);

        PageableResponse<UserDto> allUsers = userServiceI.getAllUsers(1, 2, "userId","asc" );
        Assertions.assertEquals(2,allUsers.getContent().size());
    }

    @Test
    public void updateUserTest() {
        String userId="101";


        userDto = UserDto.builder()
                .name("raj")
                .gender("male")
                .password("raj123")
                .about("java developer")
                .imageName("raj.png")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto updateUser = userServiceI.updateUser(userDto, userId);

        System.out.println(updateUser.getName());
        Assertions.assertNotNull(updateUser);

    }

    @Test
    void deleteUserTest() {

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(user));

        String deleteUser = userServiceI.deleteUser(user.getUserId());

        Mockito.verify(userRepository,Mockito.times(1)).deleteById(user.getUserId());

        Assertions.assertNull(null,"user deleted");
    }
}