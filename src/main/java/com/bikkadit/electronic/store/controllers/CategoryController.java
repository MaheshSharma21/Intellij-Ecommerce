package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CategoryDto;
import com.bikkadit.electronic.store.service.CategoryServiceI;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryServiceI categoryServiceI;

    /**
     * @param categoryDto
     * @return
     * @apiNote This api is used to create category
     * @author Mahesh Sharma
     */
    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createcategory(@Valid  @RequestBody CategoryDto categoryDto) {
        log.info("Request starting for service layer to create category");
        CategoryDto category = this.categoryServiceI.createCategory(categoryDto);
        log.info("Request completed for service layer to create category");
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    /**
     * @param categoryDto
     * @param categoryId
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is used to update category
     */
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory( @Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        log.info("Request starting for service layer to update category with categoryId :{}", categoryId);
        CategoryDto updateCategory = this.categoryServiceI.updateCategory(categoryDto, categoryId);
        log.info("Request completed for service layer to update category with categoryId :{}", categoryId);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return
     * @apiNote This api is used to delete category
     * @author Mahesh Sharma
     */
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> deletecategory(@PathVariable String categoryId) {
        log.info("Request starting for service layer to delete category with categoryId :{}", categoryId);
        this.categoryServiceI.deleteCategory(categoryId);
        ApiResponse apiResponse = ApiResponse.builder().message(" category deleted successfully ").success(true).Status(HttpStatus.OK).build();
        log.info("Request completed for service layer to delete category with categoryId :{}", categoryId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is used to get category by categoryId
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> getcategorybyId(@PathVariable String categoryId) {
        log.info("Request starting for service layer to get category with categoryId :{}", categoryId);
        CategoryDto categorybyId = this.categoryServiceI.getCategorybyId(categoryId);
        log.info("Request completed for service layer to get category with categoryId :{}", categoryId);
        return new ResponseEntity<>(categorybyId, HttpStatus.FOUND);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is used to get All categories
     */
    @GetMapping("/categories")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = AppConstant.PAGE_NUMBER, defaultValue = AppConstant.PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = AppConstant.PAGE_SIZE, defaultValue = AppConstant.PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @RequestParam(value = AppConstant.SORT_BY, defaultValue = AppConstant.SORT_BY_DEFAULT_VALUE_CATEGORY, required = false) String sortBy,
            @RequestParam(value = AppConstant.SORT_DIR, defaultValue = AppConstant.SORT_DIR_DEFAULT_VALUE, required = false) String sortDir) {
        log.info("Request starting for service layer to get All categories ");
        PageableResponse<CategoryDto> allCategories = this.categoryServiceI.getAllCategories(pageNumber, pageSize, sortBy, sortDir);
        log.info("Request completed for service layer to get All categories ");
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    /**
     * @param keyword
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is used to search category with some keyword
     */
    @GetMapping("/category/search/{keyword}")
    public ResponseEntity<List<CategoryDto>> searchCategorybytitle(@PathVariable String keyword) {
        log.info("Request starting for service layer to search category with keyword :{}", keyword);
        List<CategoryDto> categoryDtos = this.categoryServiceI.searchCategorybytitle(keyword);
        log.info("Request completed for service layer to search category with keyword :{}", keyword);
        return new ResponseEntity<>(categoryDtos, HttpStatus.FOUND);
    }
}
