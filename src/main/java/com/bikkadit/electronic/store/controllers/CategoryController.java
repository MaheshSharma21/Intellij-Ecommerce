package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CategoryDto;
import com.bikkadit.electronic.store.service.CategoryServiceI;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryServiceI categoryServiceI;

    @PostMapping
    public ResponseEntity<CategoryDto> createcategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto category = this.categoryServiceI.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {


        CategoryDto updateCategory = this.categoryServiceI.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }


    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deletecategory(@PathVariable String categoryId) {

        this.categoryServiceI.deleteCategory(categoryId);

        ApiResponse apiResponse = ApiResponse.builder().message(" category deleted successfully ").success(true).Status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getcategorybyId(@PathVariable String categoryId) {

        CategoryDto categorybyId = this.categoryServiceI.getCategorybyId(categoryId);
        return new ResponseEntity<>(categorybyId, HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(@RequestParam(value = AppConstant.PAGE_NUMBER, defaultValue = AppConstant.PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
                                                                          @RequestParam(value = AppConstant.PAGE_SIZE, defaultValue = AppConstant.PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
                                                                          @RequestParam(value = AppConstant.SORT_BY, defaultValue = AppConstant.SORT_BY_DEFAULT_VALUE, required = false) String sortBy,
                                                                          @RequestParam(value = AppConstant.SORT_DIR, defaultValue = AppConstant.SORT_DIR_DEFAULT_VALUE, required = false) String sortDir) {

        PageableResponse<CategoryDto> allCategories = this.categoryServiceI.getAllCategories(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PostMapping("/search/{keyword}")
    public ResponseEntity<List<CategoryDto>> searchCategorybytitle(@PathVariable String keywprd) {
        List<CategoryDto> categoryDtos = this.categoryServiceI.searchCategorybytitle(keywprd);
        return new ResponseEntity<>(categoryDtos, HttpStatus.FOUND);
    }
}
