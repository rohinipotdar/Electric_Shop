package com.BikkadIT.ShopElectric.controller;

import com.BikkadIT.ShopElectric.dtos.OrderDtos;
import com.BikkadIT.ShopElectric.dtos.PageableResponse;
import com.BikkadIT.ShopElectric.payloads.ApiResponse;
import com.BikkadIT.ShopElectric.services.OrderServiceI;
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



    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDtos>> getAllOrderByUser(@PathVariable String userId){
        List<OrderDtos> orderOfUser = this.orderServiceI.getOrderOfUser(userId);

        return new ResponseEntity<>(orderOfUser, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> removeOrderById(@PathVariable String orderId){

        this.orderServiceI.removeOrder(orderId);

        return new ResponseEntity<>(new ApiResponse("order Deleted ",true,HttpStatus.OK),HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse<OrderDtos>> getAllOrders(
            @RequestParam (value = "pageNumber",defaultValue = "0") int pageNumber,
            @RequestParam (value = "pageSize",defaultValue = "2") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam (value = "sortDir", defaultValue = "ASC") String sortDir){

        PageableResponse<OrderDtos> orders = this.orderServiceI.getOrders(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(orders,HttpStatus.OK);

    }



}
