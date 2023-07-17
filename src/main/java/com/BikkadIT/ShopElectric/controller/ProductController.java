package com.BikkadIT.ShopElectric.controller;

import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.dtos.ProductDto;
import com.BikkadIT.ShopElectric.dtos.UserDto;
import com.BikkadIT.ShopElectric.helper.AppConstants;
import com.BikkadIT.ShopElectric.payloads.ApiResponse;
import com.BikkadIT.ShopElectric.payloads.ImageResponse;
import com.BikkadIT.ShopElectric.services.FileService;
import com.BikkadIT.ShopElectric.services.ProductServiceI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductServiceI productServiceI;

    @Autowired
    private FileService fileService;

    @Value("${product.profile.image.path}")
    private String imagePath;
    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    /*
     * @author: rohini
     * @ApiNote:  This method is for Create Product
     * @param: ProductDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<ProductDto> createProducts(@Valid @RequestBody ProductDto productDto){
        logger.info("Request entering for the create products ");
        ProductDto product=this.productServiceI.createProduct(productDto);
        logger.info("Request completed for the create products");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
    /*
     * @author: rohini
     * @ApiNote: This method is for get single record product
     * @param: productId
     * @return
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getUser(@PathVariable String productId){
        logger.info("Request entering the get single Product with productId :{}", productId);
        ProductDto getProd=this.productServiceI.getProductById(productId);
        logger.info("Completed request for get single Product with productId :{}", productId);
        return new ResponseEntity<>(getProd, HttpStatus.OK);
    }
    /*
     * @author: rohini
     * @ApiNote: This method is for get all Products
     * @return
     */
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllUsers(
            @RequestParam (value = "pageNumber",defaultValue = "0") int pageNumber,
            @RequestParam (value = "pageSize",defaultValue = "2") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam (value = "sortDir", defaultValue = "ASC") String sortDir
    ){
        logger.info("Request entering for get All Product");
        PageableResponse<ProductDto> getAllProd=this.productServiceI.getAllProducts(pageNumber,pageSize,sortBy,sortDir);
        logger.info("Complete request for get All Product");
        return new ResponseEntity<>(getAllProd, HttpStatus.OK);
    }
    /*
     * @author: rohini
     * @ApiNote: This method is for update Products
     * @param: productDto
     * @param: productId
     * @return
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProd(@Valid @RequestBody ProductDto productDto, @PathVariable String productId){
        logger.info("Request entering for the update Product with productId :{}", productId);
        ProductDto getNewProd=this.productServiceI.updateProducts(productDto,productId);
        logger.info("Completed request for the update Product with productId :{}", productId);
        return new ResponseEntity<>(getNewProd, HttpStatus.OK);
    }
    /*
     * @author: rohini
     * @ApiNote: This method is for delete Products
     * @param: userId
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProd(@PathVariable String productId){
        logger.info("Request entering for Delete Product with productId :{}", productId);
        this.productServiceI.deleteProduct(productId);
        logger.info("Complete request for Delete Product with productId :{}", productId);
        return new ResponseEntity<>(new ApiResponse(AppConstants.USER_DELETE,true,HttpStatus.OK),HttpStatus.OK);
    }
    /*
     * @author: rohini
     * @ApiNote: This method is for get all Products by title
     * @param: title
     * @return
     */
    @GetMapping("/getallprods/{title}")
    public ResponseEntity<List<ProductDto>> getProductsBytitle(@PathVariable String title){
        logger.info("Request entering for get All Product by title ");
        List<ProductDto> allBytitle = this.productServiceI.getAllBytitle(title);
        logger.info("Complete request for get All Product by title");
        return new ResponseEntity<>(allBytitle, HttpStatus.OK);
    }

    /*
     * @author: rohini
     * @ApiNote: This method is for upload image in Products
     * @param: image, productId
     * @return
     */
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("image") MultipartFile image
            ) throws IOException {
        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = this.productServiceI.getProductById(productId);
        productDto.setProductImageName(fileName);
        ProductDto updateProducts = productServiceI.updateProducts(productDto, productId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(fileName).message("Product image uploaded successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }
//serve image
/*
 * @author: rohini
 * @ApiNote: This method is for serve image in Products
 * @param: imageresponse, productId
 */
@GetMapping("/image/{productId}")
        public void serveImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
    ProductDto product = productServiceI.getProductById(productId);
    logger.info("user image name: {}",productId);
    InputStream resource = fileService.getResource(imagePath, product.getProductImageName());
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(resource,response.getOutputStream());
}
}
