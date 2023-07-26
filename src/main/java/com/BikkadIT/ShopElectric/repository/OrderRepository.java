package com.BikkadIT.ShopElectric.repository;

import com.BikkadIT.ShopElectric.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
}
