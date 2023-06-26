package com.BikkadIT.ShopElectric.services.impl;

import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.dtos.ProductDto;
import com.BikkadIT.ShopElectric.dtos.UserDto;
import com.BikkadIT.ShopElectric.entities.Products;
import com.BikkadIT.ShopElectric.entities.User;
import com.BikkadIT.ShopElectric.exceptions.ResourceNotFoundException;
import com.BikkadIT.ShopElectric.helper.AppConstants;
import com.BikkadIT.ShopElectric.repository.ProductRepo;
import com.BikkadIT.ShopElectric.services.ProductServiceI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductServiceI{
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRepo productRepo;

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    /*
     * @author: rohini
     * @implNote:  This method is for Create product
     * @param: productDto
     * @return
     */
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        logger.info("Initiating Dao call for create product");
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        productDto.setAddedDate(new Date());
        Products products = this.mapper.map(productDto, Products.class);
        Products newProd=this.productRepo.save(products);
        ProductDto newUser1=this.mapper.map(newProd,ProductDto.class);
        logger.info("Complete Dao call for create User");
        return newUser1;

    }
    /*
     * @author: rohini
     * @implNote:  This method is for Update Product
     * @param: ProductDto
     * @param: productId
     * @return
     */

    @Override
    public ProductDto updateProducts(ProductDto productDto, String productId) {
        logger.info("Initiating Dao call for update Product by userID : {}",productId);
        this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND +productId));
        Products prod1=this.mapper.map(productDto,Products.class);
        Products newprod=this.productRepo.save(prod1);
        logger.info("complete Dao call for update User by userID : {}",productId);
        return this.mapper.map(newprod,ProductDto.class);
    }

    /*
     * @author: rohini
     * @implNote:  This method is for get single Prod
     * @param: productId
     * @return
     */
    @Override
    public ProductDto getProductById(String productId) {
        logger.info("Initiating Dao call for get single User by userID : {}",productId);
        Products newProd=this.productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException(AppConstants.NOT_FOUND+productId));
        logger.info("Complete Dao call for get single User by userID : {}",productId);
        return this.mapper.map(newProd,ProductDto.class);
    }
    /*
     * @author: rohini
     * @implNote:  This method is for get all Products
     * @return
     */
    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = Sort.by(sortBy);
        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Products> page = this.productRepo.findAll(pageable);
        List<Products> products = page.getContent();
        logger.info("Initiating Dao call for get all Products ");
        List<ProductDto> allUsers=products.stream().map(user->mapper.map(user,ProductDto.class)).collect(Collectors.toList());

        PageableResponse<ProductDto> response = new PageableResponse<>();
        response.setContent(allUsers);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());

        logger.info("Complete Dao call for get all Products ");
        return response;
    }
    /*
     * @author: rohini
     * @implNote:  This method is for get all Products by title
     * @param title
     * @return
     */
    @Override
    public List<ProductDto> getAllBytitle(String title) {
        logger.info("Initiating Dao call for get all Products by title ");
        List<Products> productsList = this.productRepo.findByTitleContaining(title);
        List<ProductDto> list = productsList.stream().map(li -> mapper.map(li, ProductDto.class)).collect(Collectors.toList());
        logger.info("complete Dao call for get all Products by title ");
        return list;
    }
    /*
     * @author: rohini
     * @implNote:  This method is for delete Products by productId
     * @param productId
     *
     */
    @Override
    public void deleteProduct(String productId) {
        logger.info("Initiating Dao call for delete Products by productId ");
        this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + productId));
        this.productRepo.deleteById(productId);
        logger.info("complete Dao call for delete Products by productId ");
    }



}
