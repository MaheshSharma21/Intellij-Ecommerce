package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.entities.Product;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.ProductDto;

import java.util.List;

public interface ProductServiceI {

    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto ,String productId);

    ProductDto getProductById(String productId);

     PageableResponse<ProductDto>  getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


    PageableResponse<ProductDto> getAllLiveProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
     void deleteProduct(String productId);

     PageableResponse<ProductDto> searchProductByTitle(String subtitle,Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


}
