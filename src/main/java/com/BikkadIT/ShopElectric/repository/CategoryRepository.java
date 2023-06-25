package com.BikkadIT.ShopElectric.repository;

import com.BikkadIT.ShopElectric.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,String> {

}