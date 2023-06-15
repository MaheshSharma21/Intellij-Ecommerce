package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.ImageResponse;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CategoryDto;
import com.bikkadit.electronic.store.payloads.UserDto;
import com.bikkadit.electronic.store.service.CategoryServiceI;

import com.bikkadit.electronic.store.service.FileServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryServiceI categoryServiceI;

    @Autowired
    private FileServiceI fileServiceI;

    @Value("${category.profile.image.path}")
    private String uploadCoverImagePath;

    /**
     * @param categoryDto
     * @return
     * @apiNote This api is used to create category
     * @author Mahesh Sharma
     */
    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@Valid  @RequestBody CategoryDto categoryDto) {
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
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId) {
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
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId) {
        log.info("Request starting for service layer to get category with categoryId :{}", categoryId);
        CategoryDto categoryById = this.categoryServiceI.getCategoryById(categoryId);
        log.info("Request completed for service layer to get category with categoryId :{}", categoryId);
        return new ResponseEntity<>(categoryById, HttpStatus.FOUND);
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
    public ResponseEntity<List<CategoryDto>> searchCategoryByTitle(@PathVariable String keyword) {
        log.info("Request starting for service layer to search category with keyword :{}", keyword);
        List<CategoryDto> categoryDto= this.categoryServiceI.searchCategoryByTitle(keyword);
        log.info("Request completed for service layer to search category with keyword :{}", keyword);
        return new ResponseEntity<>(categoryDto, HttpStatus.FOUND);
    }




    /**
     * @param file
     * @param categoryId
     * @return
     * @throws IOException
     * @author Mahesh Sharma
     * @apiNote This api is used to upload coverImage for category
     */
    @PostMapping("/category/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCoverImage(@RequestParam("categoryImage") MultipartFile file, @PathVariable String categoryId) throws IOException {
        log.info(" Request Starting for fileService layer to upload coverImage with categoryId :{}", categoryId);
        String ImageName = fileServiceI.uploadcoverImage(file, uploadCoverImagePath);

        CategoryDto category = categoryServiceI.getCategoryById(categoryId);

        category.setCoverImage(ImageName);
        CategoryDto userDto = categoryServiceI.updateCategory(category, categoryId);
        ImageResponse fileUploaded = ImageResponse.builder().imageName(ImageName).message("File uploaded ").success(true).Status(HttpStatus.CREATED).build();
        log.info(" Request completed for fileService layer to upload coverImage with categoryId :{}", categoryId);
        return new ResponseEntity<>(fileUploaded, HttpStatus.CREATED);
    }

    /**
     * @param categoryId
     * @param response
     * @throws IOException
     * @author Mahesh Sharma
     * @apiNote This api is used to server Image
     */
    @GetMapping("category/image/{categoryId}")
    public void serverImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        log.info(" Request Starting for fileService layer to serve coverImage with categoryId :{}", categoryId);
        CategoryDto user = categoryServiceI.getCategoryById(categoryId);
        log.info(" user Image Name :{}", user.getCoverImage());
        InputStream resource = fileServiceI.getcoverImage(uploadCoverImagePath, user.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        log.info(" Request completed for fileService layer to serve coverImage with categoryId :{}", categoryId);
    }
}
