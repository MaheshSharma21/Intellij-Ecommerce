package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.Product;
import com.bikkadit.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.General;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.ProductDto;
import com.bikkadit.electronic.store.repositories.ProductRepository;
import com.bikkadit.electronic.store.service.ProductServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductServiceI {


    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ModelMapper model;

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        Product prod = this.model.map(productDto, Product.class);

        //for every time random Id will be stored
        String randomId = UUID.randomUUID().toString();
        prod.setProductId(randomId);

        Product save = this.productRepo.save(prod);
        return this.model.map(save, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
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

        Product save = this.productRepo.save(prod);
        return this.model.map(save, ProductDto.class);
    }

    @Override
    public ProductDto getProductById(String productId) {
        Product prod = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(" product not found with this productId " + productId));
        return this.model.map(prod, ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepo.findAll(pageable);

        PageableResponse<ProductDto> pageableResponse = General.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }

   @Override
        public PageableResponse<ProductDto> getAllLiveProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
            Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
       Page<Product> page = this.productRepo.findByLiveTrue(pageable);

       PageableResponse<ProductDto> pageableResponse = General.getPageableResponse(page, ProductDto.class);

            return pageableResponse;
        }

        @Override
        public void deleteProduct(String productId) {

            Product prod = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(" product not found with this productId " + productId));
            this.productRepo.delete(prod);
        }

        @Override
        public PageableResponse<ProductDto> searchProductByTitle(String subtitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {


            Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

            Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);
            Page<Product> page = this.productRepo.findByTitleContaining(subtitle, pageable);

            PageableResponse<ProductDto> pageableResponse = General.getPageableResponse(page, ProductDto.class);

            return pageableResponse;
        }
}
