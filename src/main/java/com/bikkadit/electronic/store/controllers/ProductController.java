package com.bikkadit.electronic.store.controllers;


import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.ImageResponse;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CategoryDto;
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

import com.bikkadit.electronic.store.payloads.ProductDto;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("product")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto product = this.productServiceI.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }

    @PutMapping("product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {
        ProductDto product = this.productServiceI.updateProduct(productDto, productId);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @GetMapping("product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId) {
        ProductDto product = this.productServiceI.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId) {
        this.productServiceI.deleteProduct(productId);
        ApiResponse response = ApiResponse.builder().message(" Product deleted Successfully ").success(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam(value = AppConstant.PAGE_NUMBER, defaultValue = AppConstant.PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = AppConstant.PAGE_SIZE, defaultValue = AppConstant.PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @RequestParam(value = AppConstant.SORT_BY, defaultValue = AppConstant.SORT_BY_DEFAULT_VALUE_CATEGORY, required = false) String sortBy,
            @RequestParam(value = AppConstant.SORT_DIR, defaultValue = AppConstant.SORT_DIR_DEFAULT_VALUE, required = false) String sortDir
    ) {

        PageableResponse<ProductDto> response = this.productServiceI.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/products/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(
            @RequestParam(value = AppConstant.PAGE_NUMBER, defaultValue = AppConstant.PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = AppConstant.PAGE_SIZE, defaultValue = AppConstant.PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @RequestParam(value = AppConstant.SORT_BY, defaultValue = AppConstant.SORT_BY_DEFAULT_VALUE_CATEGORY, required = false) String sortBy,
            @RequestParam(value = AppConstant.SORT_DIR, defaultValue = AppConstant.SORT_DIR_DEFAULT_VALUE, required = false) String sortDir
    ) {
        PageableResponse<ProductDto> products = this.productServiceI.getAllLiveProducts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @GetMapping("/product/search/{subtitle}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProductByTitle(
            @PathVariable String subTitle,
            @RequestParam(value = AppConstant.PAGE_NUMBER, defaultValue = AppConstant.PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = AppConstant.PAGE_SIZE, defaultValue = AppConstant.PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @RequestParam(value = AppConstant.SORT_BY, defaultValue = AppConstant.SORT_BY_DEFAULT_VALUE_CATEGORY, required = false) String sortBy,
            @RequestParam(value = AppConstant.SORT_DIR, defaultValue = AppConstant.SORT_DIR_DEFAULT_VALUE, required = false) String sortDir
    ) {
        PageableResponse<ProductDto> response = this.productServiceI.searchProductByTitle(subTitle, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }



    @PatchMapping("/product/image/{productId}")
    public ResponseEntity<ImageResponse> uploadCoverImage(@RequestParam("ProductImage") MultipartFile file, @PathVariable String productId) throws IOException {
        log.info(" Request Starting for fileService layer to upload ProductImage with productId :{}", productId);
        String ImageName = fileServiceI.uploadProductImage(file, uploadProductImagePath);

        ProductDto product = productServiceI.getProductById(productId);

        product.setImageName(ImageName);
        ProductDto productDto = productServiceI.updateProduct(product, productId);
        ImageResponse fileUploaded = ImageResponse.builder().imageName(ImageName).message("File uploaded ").success(true).Status(HttpStatus.CREATED).build();
        log.info(" Request completed for fileService layer to upload ProductImage with productId :{}", productId);
        return new ResponseEntity<>(fileUploaded, HttpStatus.CREATED);
    }


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


}




