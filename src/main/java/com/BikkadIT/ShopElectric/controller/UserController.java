package com.BikkadIT.ShopElectric.controller;

import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.dtos.UserDto;
import com.BikkadIT.ShopElectric.helper.AppConstants;
import com.BikkadIT.ShopElectric.payloads.ApiResponse;
import com.BikkadIT.ShopElectric.payloads.ImageResponse;
import com.BikkadIT.ShopElectric.services.FileService;
import com.BikkadIT.ShopElectric.services.UserServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceI userServiceI;

    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

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
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam (value = "pageNumber",defaultValue = "0") int pageNumber,
            @RequestParam (value = "pageSize",defaultValue = "2") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam (value = "sortDir", defaultValue = "ASC") String sortDir
    ){
        logger.info("Request entering for get All Users");
        PageableResponse<UserDto> getAllUser=this.userServiceI.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
        logger.info("Complete request for get All Users");
        return new ResponseEntity<PageableResponse<UserDto>>(getAllUser, HttpStatus.OK);
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
       return new ResponseEntity<>(new ApiResponse(AppConstants.USER_DELETE,true,HttpStatus.OK),HttpStatus.OK);
    }
    /*
     * @author: rohini
     * @ApiNote: This method is for upload image
     * @param: image, userId
     * @return
     */
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadFile(
            @RequestParam("userImage")MultipartFile image,
    @PathVariable ("userId") String userId) throws IOException {
        logger.info("Request entering for image upload");
        String imageName = this.fileService.uploadFile(image, imageUploadPath);

        UserDto user = this.userServiceI.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userServiceI.updateUser(user, userId);

        ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        logger.info("Request complete for image upload");
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }
}
