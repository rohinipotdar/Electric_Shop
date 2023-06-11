package com.BikkadIT.ShopElectric.controller;

import com.BikkadIT.ShopElectric.dtos.UserDto;
import com.BikkadIT.ShopElectric.helper.AppConstants;
import com.BikkadIT.ShopElectric.payloads.ApiResponse;
import com.BikkadIT.ShopElectric.services.UserServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceI userServiceI;

       private static Logger logger = LoggerFactory.getLogger(UserController.class);

    /*
     * @author: rohini
     * @ApiNote:  This method is for Create User
     * @param: userDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        logger.info("Request entering for the create user ");
        UserDto createUser=this.userServiceI.createUser(userDto);
        logger.info("Request completed for the create user ");
        return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);
    }

    /*
     * @author: rohini
     * @ApiNote: This method is for get single record User
     * @param: userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
        logger.info("Request entering the get single User with userId :{}", userId);
        UserDto getUser=this.userServiceI.getUserById(userId);
        logger.info("Completed request for get single User with userId :{}", userId);
        return new ResponseEntity<>(getUser, HttpStatus.OK);
    }

    @GetMapping("/email/{email}/password/{password}")
    public ResponseEntity<UserDto> getUserBySearchString(@Valid @PathVariable String email, @PathVariable String password){
        logger.info("Request entering for search User by string ");
      UserDto newUser= this.userServiceI.getUserByEmailAndPassword(email,password);
        logger.info("Request complete for search User by string ");
      return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    /*
     * @author: rohini
     * @ApiNote: This method is for get all Users
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        logger.info("Request entering for get All Users");
        List<UserDto> getAllUser=this.userServiceI.getAllUsers();
        logger.info("Complete request for get All Users");
        return new ResponseEntity<List<UserDto>>(getAllUser, HttpStatus.OK);
    }
    /*
     * @author: rohini
     * @ApiNote: This method is for update Users
     * @param: userDto
     * @param: userId
     * @return
     */
    @PutMapping("/userId/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId){
        logger.info("Request entering for the update user with userId :{}", userId);
        UserDto getNewUser=this.userServiceI.updateUser(userDto,userId);
        logger.info("Completed request for the update user with userId :{}", userId);
        return new ResponseEntity<>(getNewUser, HttpStatus.OK);
    }
    /*
     * @author: rohini
     * @ApiNote: This method is for delete Users
     * @param: userId
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){
        logger.info("Request entering for Delete User with userId :{}", userId);
       this.userServiceI.deleteUser(userId);
        logger.info("Complete request for Delete User with userId :{}", userId);
       return new ResponseEntity<>(new ApiResponse(AppConstants.USER_DELETE,true),HttpStatus.OK);
    }
}
