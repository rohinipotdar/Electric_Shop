package com.BikkadIT.ShopElectric.controller;

import com.BikkadIT.ShopElectric.dtos.OrderDtos;
import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.payloads.ApiResponse;
import com.BikkadIT.ShopElectric.services.OrderServiceI;
import com.BikkadIT.ShopElectric.services.impl.CartServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceI orderServiceI;

    private static Logger logger= LoggerFactory.getLogger(OrderController.class);

    /*
     * @author: rohini
     * @implNote:  This method is for get all orders by user
     * @param: userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDtos>> getAllOrderByUser(@PathVariable String userId){
        logger.info("Request entering for get all orders by userId : {}", userId);
        List<OrderDtos> orderOfUser = this.orderServiceI.getOrderOfUser(userId);
        logger.info("Request complete for get all orders by userId :{}", userId);
        return new ResponseEntity<>(orderOfUser, HttpStatus.OK);
    }
    /*
     * @author: rohini
     * @implNote:  This method is for remove orders by orderId
     * @param: orderId
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> removeOrderById(@PathVariable String orderId){
        logger.info("Request entering for remove orders by orderId : {}", orderId);
        this.orderServiceI.removeOrder(orderId);
        logger.info("Request complete for remove orders by orderId : {}", orderId);
        return new ResponseEntity<>(new ApiResponse("order Deleted ",true,HttpStatus.OK),HttpStatus.OK);

    }
    /*
     * @author: rohini
     * @implNote:  This method is for getAllOrders
     * @param: pageNumber,pageSize,sortBy,sortDir
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<OrderDtos>> getAllOrders(
            @RequestParam (value = "pageNumber",defaultValue = "0") int pageNumber,
            @RequestParam (value = "pageSize",defaultValue = "2") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam (value = "sortDir", defaultValue = "ASC") String sortDir)
    {
        logger.info("Request entering for get all orders ");
        PageableResponse<OrderDtos> orders = this.orderServiceI.getOrders(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Request complete for get all orders ");
        return new ResponseEntity<>(orders,HttpStatus.OK);

    }



}
