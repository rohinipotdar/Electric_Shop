package com.BikkadIT.ShopElectric.repository;

import com.BikkadIT.ShopElectric.entities.Order;
import com.BikkadIT.ShopElectric.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {

    List<Order> findByUser(User user);

}
