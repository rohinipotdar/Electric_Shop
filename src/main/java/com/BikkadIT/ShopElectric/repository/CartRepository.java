package com.BikkadIT.ShopElectric.repository;

import com.BikkadIT.ShopElectric.entities.Cart;
import com.BikkadIT.ShopElectric.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user);


}
