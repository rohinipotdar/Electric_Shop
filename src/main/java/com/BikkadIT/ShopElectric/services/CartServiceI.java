package com.BikkadIT.ShopElectric.services;

import com.BikkadIT.ShopElectric.dtos.AddItemToCartRequest;
import com.BikkadIT.ShopElectric.dtos.CartDtos;

public interface CartServiceI {
    //add item to cart
    //case1: cart is not available for user we will create the cart and then add the cart
    //case2: cart available add the items to CART
    CartDtos addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from cart
    void removeItemFromCart(String userId,int cartItem);

    //remove all items from cart
    void clearCart(String userId);

    //get cart by user
    CartDtos getCartByUser(String userId);

}
