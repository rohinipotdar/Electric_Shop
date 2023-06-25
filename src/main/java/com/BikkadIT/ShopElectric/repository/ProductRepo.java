package com.BikkadIT.ShopElectric.repository;

import com.BikkadIT.ShopElectric.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Products,String> {
    List<Products> findByTitleContaining(String title);

    List<Products> findByLiveTrue();
}
