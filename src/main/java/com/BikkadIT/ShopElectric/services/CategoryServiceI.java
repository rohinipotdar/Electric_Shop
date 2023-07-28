package com.BikkadIT.ShopElectric.services;

import com.BikkadIT.ShopElectric.dtos.CategoryDto;
import com.BikkadIT.ShopElectric.entities.Category;

import java.util.List;

public interface CategoryServiceI {
    //create category
    CategoryDto createCategory(CategoryDto category);

    //update category
    CategoryDto updateCategory(CategoryDto category, String categoryId);

    //get category by Id
    CategoryDto getCategory(String categoryId);

    //get all categories
    List<CategoryDto> getAllCategory();

    //delete category
    String deleteCategory(String categoryId);



}
