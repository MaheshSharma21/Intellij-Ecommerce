package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.Category;
import com.bikkadit.electronic.store.entities.Product;
import com.bikkadit.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.General;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.ProductDto;
import com.bikkadit.electronic.store.repositories.CategoryRepository;
import com.bikkadit.electronic.store.repositories.ProductRepository;
import com.bikkadit.electronic.store.service.ProductServiceI;
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
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductServiceI {


    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ModelMapper model;

    @Value("${product.image.path}")
    private String uploadProductImagePath;

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Request starting for dao layer to create product ");
        Product prod = this.model.map(productDto, Product.class);

        //for every time random Id will be stored
        String randomId = UUID.randomUUID().toString();
        prod.setProductId(randomId);
        prod.setAddedDate(new Date());
        Product save = this.productRepo.save(prod);
        log.info("Request completed for dao layer to create product ");
        return this.model.map(save, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {

        log.info("Request starting for dao layer to update product  with productId :{}", productId);
        Product prod = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(" Product not found with given productId" + productId));
        Product map = this.model.map(productDto, Product.class);

        prod.setTitle(productDto.getTitle());
        prod.setLive(productDto.isLive());
        prod.setColour(productDto.getColour());
        prod.setPrice(productDto.getPrice());
        prod.setDescription(productDto.getDescription());
        prod.setWeight(productDto.getWeight());
        prod.setQuantity(productDto.getQuantity());
        prod.setStock(productDto.isStock());
        prod.setMaterialUsed(productDto.getMaterialUsed());
        prod.setImageName(productDto.getImageName());
        Product save = this.productRepo.save(prod);
        log.info("Request completed for dao layer to update product  with productId :{}", productId);
        return this.model.map(save, ProductDto.class);
    }

    @Override
    public ProductDto getProductById(String productId) {
        log.info("Request Started for dao layer to get product  with productId :{}", productId);
        Product prod = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(" product not found with this productId " + productId));
        log.info("Request completed for dao layer to get product  with productId :{}", productId);
        return this.model.map(prod, ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Request Started for dao layer to get All products ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepo.findAll(pageable);

        PageableResponse<ProductDto> pageableResponse = General.getPageableResponse(page, ProductDto.class);
        log.info("Request completed for dao layer to get All products ");
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Request Started for dao layer to get All live products ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepo.findByLiveTrue(pageable);

        PageableResponse<ProductDto> pageableResponse = General.getPageableResponse(page, ProductDto.class);
        log.info("Request completed for dao layer to get All live products  ");
        return pageableResponse;
    }

    @Override
    public void deleteProduct(String productId) {
        log.info("Request started for dao layer to delete product  with productId :{}", productId);
        Product prod = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(" product not found with this productId " + productId));


        //delete product productImage

        //full path
        String fullPath = uploadProductImagePath + prod.getImageName();

        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            log.error("product Image not found in folder :{}", ex.getMessage());
        } catch (IOException ex) {
            log.error("unable to found  product Image :{}", ex.getMessage());

        }

        //delete product
        this.productRepo.delete(prod);
        log.info("Request completed for dao layer to delete product  with productId :{}", productId);
    }

    @Override
    public PageableResponse<ProductDto> searchProductByTitle(String subtitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Request Started for dao layer to search products  with subtitle :{}", subtitle);

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepo.findByTitleContaining(subtitle, pageable);

        PageableResponse<ProductDto> pageableResponse = General.getPageableResponse(page, ProductDto.class);
        log.info("Request completed for dao layer to search products  with subtitle :{}", subtitle);
        return pageableResponse;
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        log.info("Request starting for dao layer to create product  with categoryId :{}", categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_ERROR));

        Product prod = this.model.map(productDto, Product.class);

        //for every time random id will be stored
        String randomId = UUID.randomUUID().toString();
        prod.setProductId(randomId);
        prod.setAddedDate(new Date());

        prod.setCategory(category);
        Product save = this.productRepo.save(prod);
        log.info("Request completed for dao layer to  create product with categoryId :{}", categoryId);
        return this.model.map(save, ProductDto.class);

    }

    @Override
    public ProductDto updateProductWithCategory(String productId, String categoryId) {
        log.info("Request started for dao layer to  update product with categoryId :{}", categoryId);
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_ERROR));

        Product prod = this.productRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(" Product not found with given productId" + productId));

        prod.setCategory(category);
        Product save = this.productRepo.save(prod);
        log.info("Request completed for dao layer to  update product with categoryId :{}", categoryId);
        return this.model.map(save, ProductDto.class);
    }
}
