package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.Category;

import com.bikkadit.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.General;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CategoryDto;
import com.bikkadit.electronic.store.repositories.CategoryRepository;


import com.bikkadit.electronic.store.service.CategoryServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryServiceI {
    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMapper mapper;


    @Value("${category.image.path}")
    private String uploadCoverImagePath;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("Request starting for dao layer to create category ");
        Category category = this.mapper.map(categoryDto, Category.class);

        //for every time random Id will be stored
        String randomId = UUID.randomUUID().toString();
        category.setCategoryId(randomId);

        Category save = this.categoryRepo.save(category);
        log.info("Request completed for dao layer to create category ");
        return mapper.map(save, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        log.info("Request starting for dao layer to update category with categoryId :{}", categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_ERROR));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setUpdatedBy(categoryDto.getUpdatedBy());
        category.setCreatedBy(categoryDto.getCreatedBy());
        category.setCoverImage(categoryDto.getCoverImage());
        
        Category save = this.categoryRepo.save(category);

        log.info("Request completed for dao layer to update category with categoryId :{}", categoryId);
        return mapper.map(save, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        log.info("Request starting for dao layer to delete category with categoryId :{}", categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_ERROR));

        //delete category profile coverImage

        //full path
        String fullPath = uploadCoverImagePath + category.getCoverImage();

        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            log.error("category coverImage not found in folder :{}", ex.getMessage());
        } catch (IOException ex) {
            log.error("unable to found  category coverImage :{}", ex.getMessage());

        }

        //delete category
        this.categoryRepo.delete(category);
        log.info("Request completed for dao layer to delete category with categoryId :{}", categoryId);
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        log.info("Request starting for dao layer to get category with categoryId :{}", categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_ERROR));
        log.info("Request completed for dao layer to get category with categoryId :{}", categoryId);
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Request starting for dao layer to get all categories ");
        Sort sort = (sortDir.equalsIgnoreCase(AppConstant.SORT_DIRECTION)) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = this.categoryRepo.findAll(pageable);

        PageableResponse<CategoryDto> pageableResponse = General.getPageableResponse(categoryPage, CategoryDto.class);
        log.info("Request completed for dao layer to get All categories ");
        return pageableResponse;
    }

    @Override
    public List<CategoryDto> searchCategoryByTitle(String keyword) {
        log.info("Request starting for dao layer to update category with keyword :{}", keyword);
        List<Category> containing = categoryRepo.findByTitleContaining(keyword);
        List<CategoryDto> collect = containing.stream().map(data -> this.mapper.map(data, CategoryDto.class)).collect(Collectors.toList());
        log.info("Request completed for dao layer to search category with keyword :{}", keyword);
        return collect;
    }
}
