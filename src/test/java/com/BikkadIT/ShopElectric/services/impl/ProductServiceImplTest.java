package com.BikkadIT.ShopElectric.services.impl;

import com.BikkadIT.ShopElectric.dtos.ProductDto;
import com.BikkadIT.ShopElectric.entities.Products;
import com.BikkadIT.ShopElectric.repository.ProductRepo;
import com.BikkadIT.ShopElectric.services.ProductServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @MockBean
    private ProductRepo productRepo;

    @Autowired
    private ProductServiceI productServiceI;

    @Autowired
    private ModelMapper mapper;

    Products products1;

    Products products2;

    ProductDto productDto;

    List<Products> products;

    @BeforeEach
    public void init(){
        products1=Products.builder()
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

        products2=Products.builder()
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

        products = new ArrayList<>();
        products.add(products1);
        products.add(products2);

    }

    @Test
    void createProductTest() {

        Mockito.when(productRepo.save(Mockito.any())).thenReturn(products1);

        ProductDto product = productServiceI.createProduct(mapper.map(products1, ProductDto.class));

        Assertions.assertNotNull(product);

    }

    @Test
    void updateProductsTest() {

        String productId="";

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

        Mockito.when(productRepo.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(products1));

        Mockito.when(productRepo.save(Mockito.any())).thenReturn(products1);

        ProductDto updateProducts = productServiceI.updateProducts(mapper.map(products1, ProductDto.class), products1.getProductId());

        Assertions.assertNotNull(updateProducts);

    }

    @Test
    void getProductById() {
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void getAllByTitle() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void createProductWithCategory() {
    }
}