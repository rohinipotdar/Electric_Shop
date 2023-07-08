package com.BikkadIT.ShopElectric.services.impl;

import com.BikkadIT.ShopElectric.dtos.CategoryDto;
import com.BikkadIT.ShopElectric.entities.Category;
import com.BikkadIT.ShopElectric.entities.Products;
import com.BikkadIT.ShopElectric.repository.CategoryRepo;
import com.BikkadIT.ShopElectric.services.CategoryServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {


    @MockBean
   private CategoryRepo categoryRepo;

    @Autowired
    private CategoryServiceI categoryServiceI;

    @Autowired
    private ModelMapper mapper;

    public Category category;

    public Category category1;

    List<Products> product;

    List<Category> categories;

    public CategoryDto categoryDto;

    @BeforeEach
    public void init(){

        category =Category.builder()
             .categoryId("101")
             .title("mobile phones")
             .description("all phones are android")
             .coverImage("mobile123.png")
             .products(product)
             .build();

     category1 =Category.builder()
             .categoryId("102")
             .title("iphones")
             .description("all phones are smart phones")
             .coverImage("iphones.png")
             .products(product)
             .build();

     categories = new ArrayList<>();
     categories.add(category);
     categories.add(category1);

    }

    @Test
    void createCategoryTest() {

     Mockito.when(categoryRepo.save(Mockito.any())).thenReturn(category);

     CategoryDto category1 = categoryServiceI.createCategory(categoryDto);

     Assertions.assertNotNull(category1);

    }

    @Test
    void updateCategoryTest() {

     String categoryId="";

     categoryDto =CategoryDto.builder()
             .categoryId("101")
             .title("mobile phones")
             .description("all phones are android")
             .coverImage("mobile123.png")
             .build();

     Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(category));

      Mockito.when(categoryRepo.save(Mockito.any())).thenReturn(category);

     CategoryDto updateCategory = categoryServiceI.updateCategory(categoryDto, category.getCategoryId());

     Assertions.assertNotNull(updateCategory,"category updated");

    }

    @Test
    void getCategoryTest() {
     Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(category));

     CategoryDto category1 = categoryServiceI.getCategory(category.getCategoryId());

     Assertions.assertNotNull(category1);

    }

    @Test
    void getAllCategoryTest() {

     categories = Arrays.asList(category,category1);

     Mockito.when(categoryRepo.findAll()).thenReturn(categories);

     List<CategoryDto> allCategory = categoryServiceI.getAllCategory();

     Assertions.assertNotNull(allCategory);

    }

    @Test
    void deleteCategoryTest() {

     Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(category));

     String deletedCategory = categoryServiceI.deleteCategory(category.getCategoryId());

     Assertions.assertNull(null,"category deleted");
    }
}