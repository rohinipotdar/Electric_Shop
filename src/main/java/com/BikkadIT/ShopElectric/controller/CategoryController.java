package com.BikkadIT.ShopElectric.controller;

import com.BikkadIT.ShopElectric.dtos.CategoryDto;
import com.BikkadIT.ShopElectric.helper.AppConstants;
import com.BikkadIT.ShopElectric.payloads.ApiResponse;
import com.BikkadIT.ShopElectric.services.CategoryServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceI categoryServiceI;

    private Logger logger= LoggerFactory.getLogger(CategoryController.class);

    /*
     * @author: rohini
     * @ApiNote:  This method is for Create Category
     * @param: categoryDto
     * @return
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        logger.info("Request entering for create category");
        CategoryDto category = this.categoryServiceI.createCategory(categoryDto);
        logger.info("Request complete for create category");
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
    /*
     * @author: rohini
     * @ApiNote:  This method is for update Category
     * @param: categoryDto
     * @return
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
        logger.info("Request entering for update category");
        CategoryDto category = this.categoryServiceI.updateCategory(categoryDto,categoryId);
        logger.info("Request complete for update category");
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    /*
     * @author: rohini
     * @ApiNote:  This method is for get Category
     * @param: categoryId
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryId){
        logger.info("Request entering for update category");
        CategoryDto category = this.categoryServiceI.getCategory(categoryId);
        logger.info("Request complete for get category");
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    /*
     * @author: rohini
     * @ApiNote:  This method is for get all Category
     * @param:
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        logger.info("Request entering for update category");
        List<CategoryDto> category = this.categoryServiceI.getAllCategory();
        logger.info("Request complete for get category");
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    /*
     * @author: rohini
     * @ApiNote:  This method is for get all Category
     * @param:
     * @return
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId){
        logger.info("Request entering for update category");
        String str = this.categoryServiceI.deleteCategory(categoryId);
        logger.info("Request complete for get category");
        return new ResponseEntity<>(new ApiResponse(AppConstants.USER_DELETE,true,HttpStatus.OK),HttpStatus.OK);
    }

}
