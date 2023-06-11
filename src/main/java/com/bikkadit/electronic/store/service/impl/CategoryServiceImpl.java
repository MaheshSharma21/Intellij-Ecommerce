package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.Category;

import com.bikkadit.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.General;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CategoryDto;
import com.bikkadit.electronic.store.repositories.CategoryRepository;


import com.bikkadit.electronic.store.service.CategoryServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;


public class CategoryServiceImpl implements CategoryServiceI {
    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = this.mapper.map(categoryDto, Category.class);
        Category save = this.categoryRepo.save(category);
        return mapper.map(save, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(" category not found with this categoryId"));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        Category save = this.categoryRepo.save(category);
        return mapper.map(save, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(" category not found with this categoryId"));
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategorybyId(String categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(" category not found with this categoryId"));

        return mapper.map(category,CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {

       Sort sort =(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> categoryPage = this.categoryRepo.findAll(pageable);

        PageableResponse<CategoryDto> pageableResponse = General.getPageableResponse(categoryPage, CategoryDto.class);

        return pageableResponse;
    }

    @Override
    public List<CategoryDto> searchCategorybytitle(String keyword) {
        List<Category> containing = categoryRepo.findByTitleContaining(keyword);
        List<CategoryDto> collect = containing.stream().map(data -> this.mapper.map(data, CategoryDto.class)).collect(Collectors.toList());
        return collect;
    }
}
