package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.Category;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CategoryDto;
import com.bikkadit.electronic.store.repositories.CategoryRepository;

import com.bikkadit.electronic.store.service.CategoryServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {

    @MockBean
    private CategoryRepository categoryRepo;

    @Autowired
    private CategoryServiceI categoryService;

    @Autowired
    private ModelMapper mapper;

    Category category;

    @BeforeEach
    void init() {
        category = Category.builder()
                .title(" this is a sd card related category")
                .description("SD card available for every smart phones on minimum prize")
                .coverImage("abc.png").build();

    }

    @Test
    void createCategory() {

        //Stubbing
        Mockito.when(categoryRepo.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = this.mapper.map(category, CategoryDto.class);
        //Actual method calling
        CategoryDto category1 = categoryService.createCategory(categoryDto);
        //Assertion for validating
        Assertions.assertNotNull(category1);
    }

    @Test
    void updateCategory() {
        String categoryId = "123";
        CategoryDto categoryDto = CategoryDto.builder()
                .coverImage("xyz.png")
                .title(" Anniversary pics")
                .description(" some of our 1st Anniversary pics ")
                .build();

        //Stubbing
        Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepo.save(Mockito.any())).thenReturn(category);
        //Actual method calling
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
        //Assertion for validating
        Assertions.assertEquals(category.getTitle(), updateCategory.getTitle(), " categoryTitle not matched ");
    }

    @Test
    void deleteCategory() {
        String categoryId = "ms";
        //Stubbing
        Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        //Actual method calling
        categoryService.deleteCategory(categoryId);
        //verifying
        Mockito.verify(categoryRepo, Mockito.times(1)).delete(category);
    }

    @Test
    void getCategoryById() {
        String categoryId = "ms";
        //Stubbing
        Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        //actual method calling
        CategoryDto category1 = categoryService.getCategoryById(categoryId);
        //Assertion for Matching
        Assertions.assertNotNull(category1);
        Assertions.assertEquals(category.getDescription(), category1.getDescription(), " description validation failure");
    }

    @Test
    void getAllCategories() {
        Category category1 = Category.builder()
                .coverImage("pqr.png")
                .title(" Anniversary pics")
                .description(" some of our 1st Anniversary pics ")
                .build();

        Category category2 = Category.builder()
                .coverImage("lmn.png")
                .title(" Anniversary videos")
                .description(" some of our 1st Anniversary videos ")
                .build();

        Category category3 = Category.builder()
                .coverImage("stu.png")
                .title(" Anniversary family pics")
                .description(" some of our 1st Anniversary family pics ")
                .build();


        List<Category> list = Arrays.asList(category1, category2, category3);
        Page<Category> page = new PageImpl<>(list);
        //Stubbing
        Mockito.when(categoryRepo.findAll((Pageable) Mockito.any())).thenReturn(page);
        //actual method calling
        PageableResponse<CategoryDto> allCategories = categoryService.getAllCategories(1, 2, "Title", "desc");
        //assertions checking
        Assertions.assertEquals(3, allCategories.getContent().size(), " test case failed due to not size validate");

    }

    @Test
    void searchCategoryByTitle() {
        Category category1 = Category.builder()
                .coverImage("pqr.png")
                .title(" Anniversary pics")
                .description(" some of our 1st Anniversary pics ")
                .build();

        Category category2 = Category.builder()
                .coverImage("lmn.png")
                .title(" Anniversary videos")
                .description(" some of our 1st Anniversary videos ")
                .build();

        String title = "pic";
        //Stubbing
        Mockito.when(categoryRepo.findByTitleContaining(Mockito.anyString())).thenReturn(Arrays.asList(category1, category2));
        //actual method calling
        List<CategoryDto> categoryDto = categoryService.searchCategoryByTitle(title);
        //assertion Matching
        Assertions.assertEquals(2, categoryDto.size());
    }
}