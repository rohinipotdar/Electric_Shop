package com.BikkadIT.ShopElectric.controller;

import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.dtos.UserDto;
import com.BikkadIT.ShopElectric.entities.User;
import com.BikkadIT.ShopElectric.services.UserServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private User user;

    private User user1;

    private UserDto userDto;

    @MockBean
    private UserServiceI userServiceI;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {

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
                .name("arohi")
                .email("arohi@gmail.com")
                .gender("female")
                .password("arohi123")
                .about("java developer")
                .imageName("arohi.png")
                .build();

        UserDto userDto = UserDto.builder()
                .name("aditya")
                .email("aditya@gmail.com")
                .password("aditya123")
                .gender("male")
                .about("Testing method for create")
                .imageName("aaa.png")
                .build();
    }


    @Test
    public void createUserTest() throws Exception {
        //users+Post + user data as json
        //data as jso+status created
        UserDto userDto = mapper.map(user, UserDto.class);
        Mockito.when(userServiceI.createUser(Mockito.any())).thenReturn(userDto);
        //actual request for url
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ConvertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }
    private String ConvertObjectToJsonString(Object user){
        try{

            return new ObjectMapper().writeValueAsString(user);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Test
    void getUserTest() throws Exception {

        UserDto userDto = UserDto.builder()
                .userId("123")
                .name("aditya")
                .email("aditya@gmail.com")
                .password("aditya123")
                .gender("male")
                .about("Testing method for create")
                .imageName("aaa.png")
                .build();
        UserDto userDto1 = this.mapper.map(user, UserDto.class);

        Mockito.when(userServiceI.getUserById(Mockito.anyString())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/"+user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ConvertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());

    }

    @Test
    void getUserBySearchStringTest() throws Exception {
        UserDto userDto = UserDto.builder()
                .userId("123")
                .name("aditya")
                .email("aditya@gmail.com")
                .password("aditya123")
                .gender("male")
                .about("Testing method for create")
                .imageName("aaa.png")
                .build();
        UserDto userDto1 = this.mapper.map(user, UserDto.class);

        Mockito.when(userServiceI.getUserById(Mockito.anyString())).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/email/"+userDto.getEmail()+"/password/"+userDto.getPassword())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ConvertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getAllUsersTest() throws Exception {

        UserDto userDto1 = UserDto.builder()
                .name("shlok ")
                .email("shlok@gmail.com")
                .password("shlok123")
                .gender("female")
                .about("Testing method for getting all user")
                .imageName("xyz.png")
                .build();
        UserDto userDto2 = UserDto.builder()
                .name("siya")
                .email("siya@gmail.com")
                .password("siya123")
                .gender("female")
                .about("Testing method for getting all user")
                .imageName("xyz.png")
                .build();
        UserDto userDto3 = UserDto.builder()
                .name("jiya salunke")
                .email("jiya@gmail.com")
                .password("jiya123")
                .gender("female")
                .about("Testing method for getting all user")
                .imageName("xyz.png")
                .build();

        PageableResponse<UserDto> pageableResponse= new PageableResponse<>();

        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(200);
        pageableResponse.setPageNumber(5);
        pageableResponse.setContent(Arrays.asList(userDto1,userDto2,userDto3));
        pageableResponse.setTotalPages(20);
        pageableResponse.setPageSize(2);

        Mockito.when(userServiceI.getAllUsers(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/users/all")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    void updateUser() throws Exception {
        //User/{userId}  +PUT request +json
        String userId ="123";

//               UserDto userDto = mapper.map(user, UserDto.class);
        Mockito.when(userServiceI.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(userDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/users/userId/"+userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ConvertObjectToJsonString(userDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }


    @Test
    void deleteUser() throws Exception {
        String userId ="123";

        Mockito.when(userServiceI.deleteUser(Mockito.anyString())).thenReturn("user deleted");
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/users/"+userId))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void uploadFile() {
    }

    @Test
    void serveImage() {
    }
}