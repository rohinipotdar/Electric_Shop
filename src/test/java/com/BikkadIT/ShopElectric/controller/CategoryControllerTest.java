package com.BikkadIT.ShopElectric.controller;

import com.BikkadIT.ShopElectric.dtos.CategoryDto;
import com.BikkadIT.ShopElectric.entities.Category;
import com.BikkadIT.ShopElectric.entities.Products;
import com.BikkadIT.ShopElectric.services.CategoryServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    private Category category;

    private Category category1;

    private CategoryDto categoryDto;
    List<Products> product;

    @MockBean
    private CategoryServiceI categoryServiceI;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private List<Category> categories;

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
    void createCategoryTest() throws Exception {

        CategoryDto categoryDto=mapper.map(category,CategoryDto.class);

        Mockito.when(categoryServiceI.createCategory(Mockito.any())).thenReturn(categoryDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ConvertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

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
    void updateCategory() throws Exception {

        String categoryId="101";
        categoryDto =CategoryDto.builder()
                .categoryId("101")
                .title("mobile phones")
                .description("all phones are android")
                .coverImage("mobile123.png")
                .build();

        Mockito.when(categoryServiceI.updateCategory(Mockito.any(),Mockito.anyString())).thenReturn(categoryDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/categories/"+categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ConvertObjectToJsonString(categoryDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void getCategoryTest() throws Exception {
        category =Category.builder()
                .categoryId("101")
                .title("mobile phones")
                .description("all phones are android")
                .coverImage("mobile123.png")
                .products(product)
                .build();

        CategoryDto categoryDto=mapper.map(category,CategoryDto.class);

        Mockito.when(categoryServiceI.getCategory(Mockito.anyString())).thenReturn(categoryDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/"+category.getCategoryId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ConvertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void getAllCategory() {
    }

    @Test
    void deleteCategoryTest() throws Exception {
        String categoryId="123";

        Mockito.when(categoryServiceI.deleteCategory(Mockito.anyString())).thenReturn("category deleted");
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/categories/"+categoryId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void createProductWithCategory() {
    }
}