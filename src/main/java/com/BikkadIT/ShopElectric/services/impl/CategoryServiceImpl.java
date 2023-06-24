package com.BikkadIT.ShopElectric.services.impl;

import com.BikkadIT.ShopElectric.dtos.CategoryDto;
import com.BikkadIT.ShopElectric.entities.Category;
import com.BikkadIT.ShopElectric.exceptions.ResourceNotFoundException;
import com.BikkadIT.ShopElectric.helper.AppConstants;
import com.BikkadIT.ShopElectric.repository.CategoryRepo;
import com.BikkadIT.ShopElectric.services.CategoryServiceI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryServiceI {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepo categoryRepo;

    private static Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    /**
     * @author: rohini
     * @implNote:  This method is for Create category
     * @param category
     * @return
     */
    @Override
    public CategoryDto createCategory(CategoryDto category) {
        logger.info("Initializing dto call for create category");
        Category category1 = this.mapper.map(category, Category.class);
        String categoryId = UUID.randomUUID().toString();
        category1.setCategoryId(categoryId);
        Category save = this.categoryRepo.save(category1);
        logger.info("complete dto call for create category");
        return this.mapper.map(save,CategoryDto.class);

    }
    /**
     * @author: rohini
     * @implNote:  This method is for update category
     * @param categoryDto, categoryId
     * @return
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        logger.info("Initializing dto call for update category : {}",categoryId);
        Category category = this.mapper.map(categoryDto, Category.class);
        this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + categoryId));
        Category save = this.categoryRepo.save(category);
        logger.info("complete dto call for update category : {}",categoryId);
        return this.mapper.map(save,CategoryDto.class);
    }
    /**
     * @author: rohini
     * @implNote:  This method is for get category
     * @param  categoryId
     * @return
     */
    @Override
    public CategoryDto getCategory(String categoryId) {

        logger.info("Initializing dto call for get category : {}",categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + categoryId));
        logger.info("complete dto call for get category : {}",categoryId);
        return this.mapper.map(category,CategoryDto.class);
    }
    /**
     * @author: rohini
     * @implNote:  This method is for get all categories
     * @param
     * @return
     */
    @Override
    public List<CategoryDto> getAllCategory() {
        logger.info("Initializing dto call for get all categories ");
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> list = categories.stream().map(li -> mapper.map(li, CategoryDto.class)).collect(Collectors.toList());
        logger.info("complete dto call for get all categories ");
        return list;
    }
    /**
     * @author: rohini
     * @implNote:  This method is for delete category
     * @param categoryId
     * @return
     */
    @Override
    public String deleteCategory(String categoryId) {
        logger.info("Initializing dto call for delete category : {}",categoryId);
        this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + categoryId));
         this.categoryRepo.deleteById(categoryId);
        logger.info("complete dto call for get category : {}",categoryId);
        return AppConstants.USER_DELETE;
    }
}
