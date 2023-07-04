package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.Category;
import com.bikkadit.electronic.store.entities.Product;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.ProductDto;
import com.bikkadit.electronic.store.repositories.CategoryRepository;
import com.bikkadit.electronic.store.repositories.ProductRepository;
import com.bikkadit.electronic.store.service.ProductServiceI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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


@SpringBootTest
class ProductServiceImplTest {

    @MockBean
    private ProductRepository productRepo;

    @MockBean
    private CategoryRepository categoryRepo;

    @Autowired

    private ProductServiceImpl productServiceI;

    @Autowired
    private ModelMapper mapper;

    Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" mobile.png")
                .discountPrice(85000.00)
                .colour("blue")
                .price(95000.00)
                .materialUsed(" some light particles ")
                .weight(100)
                .stock(true).build();
    }

    @Test
    void createProduct() {
        ProductDto productdto = this.mapper.map(product, ProductDto.class);

        //Stubbing
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);

        //Actual Method calling
        ProductDto product1 = productServiceI.createProduct(productdto);

        //Assertion
        Assertions.assertNotNull(product1);
        Assertions.assertEquals(product.getColour(), product1.getColour(), "Colour not matched with expected");


    }

    @Test
    void updateProduct() {

        ProductDto productDto = ProductDto.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" mobile1.png")
                .discountPrice(85000.00)
                .colour("blue")
                .price(95000.00)
                .materialUsed(" some light particles ")
                .weight(200)
                .stock(true).build();

        String productId = "abcd";

        Mockito.when(productRepo.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);

        ProductDto productDto1 = productServiceI.updateProduct(productDto, productId);

        Assertions.assertEquals(productDto1.getPrice(), product.getPrice(), " test case failed !!");
        Assertions.assertNotNull(productDto1);


    }

    @Test
    void getProductById() {

        String productId = "abcds";
        ProductDto productdto = this.mapper.map(product, ProductDto.class);

        Mockito.when(productRepo.findById(Mockito.anyString())).thenReturn(Optional.of(product));

        ProductDto product1 = productServiceI.getProductById(productId);

        Assertions.assertNotNull(product);
        Assertions.assertEquals("blue", product.getColour(), " test case failed !!");
    }

    @Test
    void getAllProducts() {
        Product product1 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" mobile.png")
                .discountPrice(85000.00)
                .colour("blue")
                .price(95000.00)
                .materialUsed(" some light particles ")
                .weight(100)
                .stock(true).build();
        Product product2 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" mobile.png")
                .discountPrice(85000.00)
                .colour("blue")
                .price(95000.00)
                .materialUsed(" some light particles ")
                .weight(100)
                .stock(true).build();

        Product product3 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" mobile.png")
                .discountPrice(85000.00)
                .colour("blue")
                .price(95000.00)
                .materialUsed(" some light particles ")
                .weight(100)
                .stock(true).build();

        Product product4 = product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(false)
                .imageName(" mobile.png")
                .discountPrice(85000.00)
                .colour("blue")
                .price(95000.00)
                .materialUsed(" some light particles ")
                .weight(100)
                .stock(true).build();

        List<Product> list = Arrays.asList(product1, product2, product3, product4);

        Page<Product> page = new PageImpl<>(list);

        Mockito.when(productRepo.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<ProductDto> response = productServiceI.getAllProducts(1, 2, "colour", "asc");

        Assertions.assertEquals(4, response.getContent().size(), " test case failed ");

    }

    @Test
    void getAllLiveProducts() {

        Product product1 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" mobile.png")
                .discountPrice(85000.00)
                .colour("blue")
                .price(95000.00)
                .materialUsed(" some light particles ")
                .weight(100)
                .stock(true).build();
        Product product2 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" mobile.png")
                .discountPrice(85000.00)
                .colour("blue")
                .price(95000.00)
                .materialUsed(" some light particles ")
                .weight(100)
                .stock(true).build();

        Product product3 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" mobile.png")
                .discountPrice(85000.00)
                .colour("blue")
                .price(95000.00)
                .materialUsed(" some light particles ")
                .weight(100)
                .stock(true).build();



        List<Product> list = Arrays.asList(product1, product2, product3);
        Page<Product> page = new PageImpl<>(list);
        Mockito.when(productRepo.findByLiveTrue((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> response = productServiceI.getAllLiveProducts(0,1,"colour","asc");
        Assertions.assertEquals(3, response.getContent().size(), " test case failed ");
    }

    @Test
    void deleteProduct() {

        String productId ="xyz";
        Mockito.when(productRepo.findById(Mockito.anyString())).thenReturn(Optional.of(product));

        productServiceI.deleteProduct(productId);

        Mockito.verify(productRepo,Mockito.times(1)).delete(product);
    }

    @Test
    void searchProductByTitle() {

        String keyword ="azs";
        Product product1 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" mobile.png")
                .discountPrice(85000.00)
                .colour("blue")
                .price(95000.00)
                .materialUsed(" some light particles ")
                .weight(100)
                .stock(true).build();
        Product product2 = Product.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" mobile.png")
                .discountPrice(85000.00)
                .colour("blue")
                .price(95000.00)
                .materialUsed(" some light particles ")
                .weight(100)
                .stock(true).build();


        List<Product> list = Arrays.asList(product1, product2);
        Page<Product> page = new PageImpl<>(list);
        Mockito.when(productRepo.findByTitleContaining( Mockito.anyString(),Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> response = productServiceI.searchProductByTitle(keyword,0,1,"colour","asc");
        Assertions.assertEquals(2, response.getContent().size(), " test case failed ");


    }

    @Test
    void createProductWithCategory() {

        String categoryId ="av";


         Category category = Category.builder()
                .title(" this is a sd card related category")
                .description("SD card available for every smart phones on minimum prize")
                .coverImage("abc.png").build();

        Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.of(category));

        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);

        ProductDto productdto = this.mapper.map(product, ProductDto.class);

        productServiceI.createProductWithCategory(productdto,categoryId);

        Assertions.assertEquals(product.getDiscountPrice(),productdto.getDiscountPrice()," test case failed due to inEquality in validation");
    }

    @Test
    void updateProductWithCategory() {
    }
}