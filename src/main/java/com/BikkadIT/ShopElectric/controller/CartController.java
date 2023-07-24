package com.BikkadIT.ShopElectric.controller;

import com.BikkadIT.ShopElectric.dtos.AddItemToCartRequest;
import com.BikkadIT.ShopElectric.dtos.CartDtos;
import com.BikkadIT.ShopElectric.payloads.ApiResponse;
import com.BikkadIT.ShopElectric.services.CartServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartServiceI cartServiceI;

    @PostMapping("/userId")
    public ResponseEntity<CartDtos> addItemToCart(@PathVariable String userId,@RequestBody AddItemToCartRequest request){
        CartDtos cartDtos = cartServiceI.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDtos, HttpStatus.CREATED);

    }

    @GetMapping("/userId")
    public ResponseEntity<CartDtos> getCartByUser(@PathVariable String userId){
        CartDtos cartByUser = cartServiceI.getCartByUser(userId);
        return new ResponseEntity<>(cartByUser,HttpStatus.OK);
    }

    @DeleteMapping("userId/{cartItemId}/itemId")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId,@PathVariable int itemId){
        cartServiceI.removeItemFromCart(userId,itemId);
        return new ResponseEntity<>(new ApiResponse("Item deleted successfully",true,HttpStatus.OK),HttpStatus.OK);
    }

    @DeleteMapping("/clearCard/{userId}")
    public ResponseEntity<ApiResponse> clearCartByUserId(@PathVariable String userId){
        cartServiceI.clearCart(userId);
        return new ResponseEntity<>(new ApiResponse("cart cleared successfully",true,HttpStatus.OK),HttpStatus.OK);
    }

}
