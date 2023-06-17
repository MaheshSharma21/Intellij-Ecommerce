package com.bikkadit.electronic.store.controllers;


import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.ImageResponse;
import com.bikkadit.electronic.store.helper.PageableResponse;

import com.bikkadit.electronic.store.payloads.ProductDto;
import com.bikkadit.electronic.store.service.FileServiceI;
import com.bikkadit.electronic.store.service.ProductServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProductController {


    @Autowired
    private ProductServiceI productServiceI;

    @Autowired
    private FileServiceI fileServiceI;

    @Value("${product.image.path}")
    private String uploadProductImagePath;


    /**
     * @param productDto
     * @return
     * @author Mahesh Sharma
     * @apiNote this api is to create product
     */
    @PostMapping("product")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Request starting for service layer to create product ");
        ProductDto product = this.productServiceI.createProduct(productDto);
        log.info("Request completed for service layer to create product  ");
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }

    /**
     * @param productDto
     * @param productId
     * @return
     * @auhtor Mahesh Sharma
     * @apiNote This api is used to update product
     */
    @PutMapping("product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {
        log.info("Request starting for service layer to update product with productId :{}", productId);
        ProductDto product = this.productServiceI.updateProduct(productDto, productId);
        log.info("Request completed for service layer to update product with productId :{}", productId);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    /**
     * @param productId
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is for getting product by productId
     */
    @GetMapping("product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId) {
        log.info("Request starting for service layer to get product with productId :{}", productId);
        ProductDto product = this.productServiceI.getProductById(productId);
        log.info("Request complete for service layer to get product with productId :{}", productId);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    /**
     * @param productId
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is to delete product by productId
     */
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId) {
        log.info("Request starting for service layer to get product with productId :{}", productId);
        this.productServiceI.deleteProduct(productId);
        ApiResponse response = ApiResponse.builder().message(" Product deleted Successfully ").success(true).build();
        log.info("Request completed for service layer to get product with productId :{}", productId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is to get all products
     */
    @GetMapping("/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam(value = AppConstant.PAGE_NUMBER, defaultValue = AppConstant.PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = AppConstant.PAGE_SIZE, defaultValue = AppConstant.PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @RequestParam(value = AppConstant.SORT_BY, defaultValue = AppConstant.SORT_BY_DEFAULT_VALUE_CATEGORY, required = false) String sortBy,
            @RequestParam(value = AppConstant.SORT_DIR, defaultValue = AppConstant.SORT_DIR_DEFAULT_VALUE, required = false) String sortDir
    ) {
        log.info("Request starting for service layer to get ALl products ");

        PageableResponse<ProductDto> response = this.productServiceI.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        log.info("Request completed for service layer to get  All products ");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is used to get all live products
     */
    @GetMapping("/products/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(
            @RequestParam(value = AppConstant.PAGE_NUMBER, defaultValue = AppConstant.PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = AppConstant.PAGE_SIZE, defaultValue = AppConstant.PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @RequestParam(value = AppConstant.SORT_BY, defaultValue = AppConstant.SORT_BY_DEFAULT_VALUE_CATEGORY, required = false) String sortBy,
            @RequestParam(value = AppConstant.SORT_DIR, defaultValue = AppConstant.SORT_DIR_DEFAULT_VALUE, required = false) String sortDir
    ) {
        log.info("Request starting for service layer to get ALl live products ");
        PageableResponse<ProductDto> products = this.productServiceI.getAllLiveProducts(pageNumber, pageSize, sortBy, sortDir);
        log.info("Request completed for service layer to get ALl Live products ");
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    /**
     * @param subTitle
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is used to search products by title
     */

    @GetMapping("/product/search/{subtitle}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProductByTitle(
            @PathVariable String subTitle,
            @RequestParam(value = AppConstant.PAGE_NUMBER, defaultValue = AppConstant.PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = AppConstant.PAGE_SIZE, defaultValue = AppConstant.PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @RequestParam(value = AppConstant.SORT_BY, defaultValue = AppConstant.SORT_BY_DEFAULT_VALUE_CATEGORY, required = false) String sortBy,
            @RequestParam(value = AppConstant.SORT_DIR, defaultValue = AppConstant.SORT_DIR_DEFAULT_VALUE, required = false) String sortDir
    ) {
        log.info("Request starting for service layer to search product with subTitle :{}", subTitle);
        PageableResponse<ProductDto> response = this.productServiceI.searchProductByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
        log.info("Request completed for service layer to search product with subTitle :{}", subTitle);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    /**
     * @param file
     * @param productId
     * @return
     * @throws IOException
     * @author Mahesh Sharma
     * @apiNote This api is for uploading productImage
     */

    @PatchMapping("/product/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@RequestParam("ProductImage") MultipartFile file, @PathVariable String productId) throws IOException {
        log.info(" Request Starting for fileService layer to upload ProductImage with productId :{}", productId);
        String ImageName = fileServiceI.uploadProductImage(file, uploadProductImagePath);

        ProductDto product = productServiceI.getProductById(productId);

        product.setImageName(ImageName);
        ProductDto productDto = productServiceI.updateProduct(product, productId);
        ImageResponse fileUploaded = ImageResponse.builder().imageName(ImageName).message("File uploaded ").success(true).Status(HttpStatus.CREATED).build();
        log.info(" Request completed for fileService layer to upload ProductImage with productId :{}", productId);
        return new ResponseEntity<>(fileUploaded, HttpStatus.CREATED);
    }


    /**
     * @param productId
     * @param response
     * @throws IOException
     * @author Mahesh Sharma
     * @apiNote This api is used to serve productImage
     */
    @GetMapping("product/image/{productId}")
    public void serverImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        log.info(" Request Starting for fileService layer to serve ProductImage with productId :{}", productId);
        ProductDto product = productServiceI.getProductById(productId);
        log.info(" category coverImage Name :{}", product.getImageName());
        InputStream resource = fileServiceI.getCoverImage(uploadProductImagePath, product.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        log.info(" Request completed for fileService layer to serve productImage with productId :{}", productId);
    }


    /**
     * @param  productDto
     * @param categoryId
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is to create product with category id
     */
    @PostMapping("/category/{categoryId}/product")
    public ResponseEntity<ProductDto> createProductWithCategoryId(@Valid @RequestBody ProductDto productDto, @PathVariable String categoryId) {
        log.info("Request started for service layer to create product with categoryId :{}", categoryId);
        ProductDto product = this.productServiceI.createProductWithCategory(productDto, categoryId);
        log.info("Request completed for service layer to create product with categoryId :{}", categoryId);
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }

    /**
     * @param categoryId
     * @param productId
     * @return
     * @author Mahesh Sharma
     * @apiNote This api is used to update product with categoryId
     */
    @PatchMapping("/category/{categoryId}/product/{productId}")
    public ResponseEntity<ProductDto> updateProductWithCategoryId(@PathVariable String categoryId, @PathVariable String productId) {
        log.info("Request started for service layer to update product with categoryId :{}", categoryId + " and with productId :{}" + productId);
        ProductDto productDto = this.productServiceI.updateProductWithCategory(categoryId, categoryId);
        log.info("Request completed for service layer to update product with categoryId :{}", categoryId + " and with productId :{}" + productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);

    }

}




