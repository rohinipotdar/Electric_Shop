package com.BikkadIT.ShopElectric.services.impl;

import com.BikkadIT.ShopElectric.dtos.AddItemToCartRequest;
import com.BikkadIT.ShopElectric.dtos.CartDtos;
import com.BikkadIT.ShopElectric.entities.Cart;
import com.BikkadIT.ShopElectric.entities.CartItem;
import com.BikkadIT.ShopElectric.entities.Products;
import com.BikkadIT.ShopElectric.entities.User;
import com.BikkadIT.ShopElectric.exceptions.BadApiRequestException;
import com.BikkadIT.ShopElectric.exceptions.ResourceNotFoundException;
import com.BikkadIT.ShopElectric.repository.CartItemRepository;
import com.BikkadIT.ShopElectric.repository.CartRepository;
import com.BikkadIT.ShopElectric.repository.ProductRepo;
import com.BikkadIT.ShopElectric.repository.UserRepository;
import com.BikkadIT.ShopElectric.services.CartServiceI;
import com.BikkadIT.ShopElectric.services.ProductServiceI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartServiceI {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper mapper;

    private static Logger logger= LoggerFactory.getLogger(CartServiceImpl.class);

    /*
     * @author: rohini
     * @implNote:  This method is for addItem to cart
     * @param: userId,request
     * @return
     */
    @Override
    public CartDtos addItemToCart(String userId, AddItemToCartRequest request) {
        logger.info("Initiating Dao call for addItem by userId and request : {}", userId,request);
        String productId = request.getProductId();
        int quantity = request.getQuantity();

        if(quantity<=0){
            throw new BadApiRequestException("Requested quantity is not valid");
        }

        Products products = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found"));
        //fetch the user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        Cart cart=null;
        try {
             cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e){
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt((new Date()));
        }
        AtomicReference<Boolean> updated= new AtomicReference<>(false);
        //perform cart operations
        List<CartItem> items = cart.getItems();
        List<CartItem> updatedList = items.stream().map(item -> {
            if (item.getProducts().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice((int) (quantity * products.getPrice()));
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems((updatedList));

        //create items
          if(!updated.get()){
        CartItem cartItem = CartItem.builder()
            .quantity(quantity)
            .totalPrice((int) (quantity * products.getPrice()))
            .cart(cart)
            .products(products)
            .build();
        cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart savecart = cartRepository.save(cart);
        logger.info("complete Dao call for addItemToCart");
        return mapper.map(savecart,CartDtos.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        logger.info("Initiating Dao call for removeItem by userId and cartItem : {}", userId,cartItem);
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found"));
        logger.info("complete Dao call for removeItem by userId and cartItem : {}", userId,cartItem);
        cartItemRepository.delete(cartItem1);

    }

    @Override
    public void clearCart(String userId) {
        logger.info("Initiating Dao call for clear cart by userId {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        logger.info("complete Dao call for clear cart by userId {}", userId);
        cartRepository.delete(cart);

    }

    @Override
    public CartDtos getCartByUser(String userId) {
        logger.info("Initiating Dao call for get cart by userId {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        logger.info("complete Dao call for get cart by userId {}", userId);
        return mapper.map(cart,CartDtos.class);
    }
}
