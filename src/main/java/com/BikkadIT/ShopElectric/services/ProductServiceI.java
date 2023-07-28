package com.BikkadIT.ShopElectric.services;

import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.dtos.ProductDto;
import com.BikkadIT.ShopElectric.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProductServiceI {
    //create prod
    ProductDto createProduct(ProductDto productDto);

    //update
    ProductDto updateProducts(ProductDto productDto, String productId);

    //get prod by id
    ProductDto getProductById(String productId);

    //get All Products
    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get products by title
    List<ProductDto> getAllBytitle(String title);

    //delete product
    void deleteProduct(String productId);

    //create product with category
    ProductDto createProductWithCategory(ProductDto productDto, String categoryId);



}
