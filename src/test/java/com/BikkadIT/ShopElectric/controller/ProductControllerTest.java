package com.BikkadIT.ShopElectric.controller;

import com.BikkadIT.ShopElectric.dtos.CategoryDto;
import com.BikkadIT.ShopElectric.dtos.ProductDto;
import com.BikkadIT.ShopElectric.entities.Products;
import com.BikkadIT.ShopElectric.payloads.ApiResponse;
import com.BikkadIT.ShopElectric.services.ProductServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    private Products products;

    private Products products1;

    private ProductDto productDto;

    private List<Products> productsList;

    @MockBean
    private ProductServiceI productServiceI;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){

        products=Products.builder()
                .productId("101")
                .title("mobile")
                .description("samsung mobile phones")
                .price(25000.00)
                .quantity(15)
                .live(true)
                .stock(true)
                .addedDate(new Date())
                .discount(10.50)
                .build();

        products1=Products.builder()
                .productId("102")
                .title("washing machine")
                .description("fully autometic machines")
                .price(45000.00)
                .quantity(15)
                .live(true)
                .stock(true)
                .addedDate(new Date())
                .discount(05.20)
                .build();

    }

    @Test
    void createProductsTest() throws Exception {

        ProductDto productDto1 = mapper.map(products1, ProductDto.class);

        Mockito.when(productServiceI.createProduct(Mockito.any())).thenReturn(productDto1);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ConvertObjectToJsonString(products1))
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
    void getProductByIdTest() throws Exception {

        ProductDto productDto1 = mapper.map(products1, ProductDto.class);
        Mockito.when(productServiceI.getProductById(Mockito.anyString())).thenReturn(productDto1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products/"+products1.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ConvertObjectToJsonString(products1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    void updateProdTest() throws Exception {
        String productId="123";

        productDto=ProductDto.builder()
                .title("mobile")
                .description("samsung mobile phones")
                .price(25000.00)
                .quantity(15)
                .live(true)
                .stock(true)
                .addedDate(new Date())
                .discount(10.50)
                .build();

        Mockito.when(productServiceI.updateProducts(Mockito.any(),Mockito.anyString())).thenReturn(productDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/"+productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ConvertObjectToJsonString(products))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void deleteProdTest() throws Exception {
        /*String productId="123";

        ApiResponse api=new ApiResponse("product deleted",true, HttpStatus.OK);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/products/"+productId))
                .andDo(print())
                .andExpect(status().isOk());*/
    }

    @Test
    void getProductsBytitle() throws Exception {
        ProductDto productDto1 = mapper.map(products1, ProductDto.class);
        Mockito.when(productServiceI.getProductById(Mockito.anyString())).thenReturn(productDto1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products/getallprods/"+products1.getTitle())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ConvertObjectToJsonString(products1))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void getAllProductsTest(){

    }


    @Test
    void uploadProductImage() {
    }

    @Test
    void serveImage() {
    }
}