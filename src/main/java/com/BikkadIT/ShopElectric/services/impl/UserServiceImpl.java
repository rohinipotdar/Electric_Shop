package com.BikkadIT.ShopElectric.services.impl;

import com.BikkadIT.ShopElectric.entities.User;
import com.BikkadIT.ShopElectric.dtos.UserDto;
import com.BikkadIT.ShopElectric.exceptions.ResourceNotFoundException;
import com.BikkadIT.ShopElectric.helper.AppConstants;
import com.BikkadIT.ShopElectric.repository.UserRepository;
import com.BikkadIT.ShopElectric.services.UserServiceI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServiceI {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    /*
     * @author: rohini
     * @implNote:  This method is for Create User
     * @param: userDto
     * @return
     */

    @Override
        public UserDto createUser(UserDto userDto) {
       logger.info("Initiating Dao call for create User");
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
       User user=this.mapper.map(userDto,User.class);
       User newuser=this.userRepository.save(user);
       UserDto newUser1=this.mapper.map(newuser,UserDto.class);
        logger.info("Complete Dao call for create User");
        return newUser1;
    }
    /*
     * @author: rohini
     * @implNote:  This method is for get single User
     * @param: userId
     * @return
     */
    @Override
    public UserDto getUserById(String userId) {
        logger.info("Initiating Dao call for get single User by userID : {}",userId);
        User user=this.mapper.map(userId,User.class);
        User newuser=this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND+userId));
        logger.info("Complete Dao call for get single User by userID : {}",userId);
        return this.mapper.map(newuser,UserDto.class);
    }
    /*
     * @author: rohini
     * @implNote:  This method is for get User by email
     * @param: email
     * @return
     */
    public UserDto getUserByEmailAndPassword (String email, String password)  {

        logger.info("Initiating Dao call for get single User by email : {}",email);
        User user=this.userRepository.findByEmailAndPassword(email,password).orElseThrow(()->new ResourceNotFoundException("User email and password not found"));
        logger.info("Complete Dao call for get single User by email : {}",email);
        return this.mapper.map(user, UserDto.class);
    }
    /*
     * @author: rohini
     * @implNote:  This method is for get all User
     * @return
     */
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=this.userRepository.findAll();
        logger.info("Initiating Dao call for get all User ");
        List<UserDto> allUsers=users.stream().map(user->mapper.map(user,UserDto.class)).collect(Collectors.toList());
        logger.info("Complete Dao call for get all User ");
        return allUsers;
    }
    /*
     * @author: rohini
     * @implNote:  This method is for Update User
     * @param: userDto
     * @param: userId
     * @return
     */
    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info("Initiating Dao call for update User by userID : {}",userId);
        User user1=this.mapper.map(userDto,User.class);
        User updateuser=this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND +userId));
        User newuser=this.userRepository.save(user1);
        logger.info("complete Dao call for update User by userID : {}",userId);
        return this.mapper.map(newuser,UserDto.class);
    }
    /*
     * @author: rohini
     * @implNote:  This method is for Delete User
     * @param: userId
     */
    @Override
    public String deleteUser(String userId) {
        logger.info("Entering Dao call for delete the User by userID : {}", userId);
        this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        this.userRepository.deleteById(userId);
        logger.info("complete Dao call for delete the User by userID : {}", userId);
        return AppConstants.USER_DELETE;
    }
}
