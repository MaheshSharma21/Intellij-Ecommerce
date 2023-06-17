package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.ProductDto;
import com.bikkadit.electronic.store.service.ProductServiceI;
import org.springframework.stereotype.Service;

import java.util.List;

@Service


public class ProductServiceImpl implements ProductServiceI {



    @Override
    public ProductDto createProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        return null;
    }

    @Override
    public ProductDto getProductById(String productId) {
        return null;
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public List<ProductDto> getAllLiveProducts() {
        return null;
    }

    @Override
    public void deleteProduct(String productId) {

    }

    @Override
    public PageableResponse<ProductDto> searchProductByTitle(String subtitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        return null;
    }

    @Override
    public ProductDto updateProductWithCategory(ProductDto productDto, String categoryId) {
        return null;
    }
}
