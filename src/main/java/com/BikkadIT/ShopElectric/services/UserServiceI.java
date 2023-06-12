package com.BikkadIT.ShopElectric.services;


import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.dtos.UserDto;
import com.BikkadIT.ShopElectric.entities.User;

import java.util.List;

public interface UserServiceI {
   //create user
   UserDto createUser(UserDto userDto);

   //get single user by id
   UserDto getUserById(String userId);

   //get all users
   PageableResponse<UserDto> getAllUsers (int pageNumber, int pageSize, String sortBy, String sortDir);

   //update user
   UserDto updateUser(UserDto userDto,String userId);

   //delete user
   String deleteUser(String userId);

   UserDto getUserByEmailAndPassword(String email, String password);

}
