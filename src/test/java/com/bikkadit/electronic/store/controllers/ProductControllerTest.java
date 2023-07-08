package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.entities.Category;
import com.bikkadit.electronic.store.entities.Product;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CategoryDto;
import com.bikkadit.electronic.store.payloads.ProductDto;
import com.bikkadit.electronic.store.service.ProductServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductServiceI productServiceI;

    @Autowired
    private ModelMapper mapper;


    @Autowired
    private MockMvc mockMvc;

    Product product;
    @BeforeEach
    void init (){
       product = Product.builder()
               .title(" Mobile phones ")
               .description(" Mobile phones above 1 lac with blue background ")
               .live(true)
               .imageName(" mobile.png")
               .discountPrice(85000.00)
               .colour("blue")
               .price(95000.00)
               .materialUsed("light particles  ....")
               .weight(100)
               .quantity(210)
               .stock(true).build();

    }
    @Test
    void createProductTest() throws Exception {
        ProductDto productDto = this.mapper.map(product, ProductDto.class);

        Mockito.when(productServiceI.createProduct(Mockito.any())).thenReturn(productDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
        .content(convertObjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.weight").exists());

    }

    private String convertObjectToJsonString(Object product) {

        try {
            return new ObjectMapper().writeValueAsString(product);
        }catch(Exception e ){
            e.printStackTrace();
            return  null;
        }
    }

    @Test
    void updateProductTest() throws Exception {
        String productId ="1234";

        ProductDto productdto = ProductDto.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" phone.png")
                .discountPrice(75000.00)
                .colour("red")
                .price(95000.00)
                .materialUsed(" some light particles... ")
                .weight(100)
                .stock(false).build();


        Mockito.when(productServiceI.updateProduct(Mockito.any(),Mockito.anyString())).thenReturn(productdto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/product/"+productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weight").exists());


    }

    @Test
    void getProductByIdTest() throws Exception {

        String productId ="klm";
        ProductDto productDto = this.mapper.map(product, ProductDto.class);
        Mockito.when(productServiceI.getProductById(Mockito.anyString())).thenReturn(productDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/product/"+productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.colour").exists());


    }

    @Test
    void deleteProductTest() {
    }

    @Test
    void getAllProductsTest() throws Exception {

        ProductDto product1 = ProductDto.builder()
                .title(" cricket kit  ")
                .description(" cricket kits above 1 lac  ")
                .live(true)
                .imageName(" bat.png")
                .discountPrice(65000.00)
                .colour("skyBlue")
                .price(75000.00)
                .materialUsed(" some light particles... ")
                .weight(50)
                .stock(false).build();

        ProductDto product2 = ProductDto.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" phone.png")
                .discountPrice(75000.00)
                .colour("red")
                .price(95000.00)
                .materialUsed(" some light particles... ")
                .weight(100)
                .stock(false).build();

        ProductDto product3 = ProductDto.builder()
                .title(" table tennis  ")
                .description(" table tennis kit below 2 lac  ")
                .live(true)
                .imageName(" tennis.png")
                .discountPrice(45000.00)
                .colour("red")
                .price(55000.00)
                .materialUsed(" some light particles... used")
                .weight(70)
                .stock(false).build();

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(product1,product2,product3));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(productServiceI.getAllProducts(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllLiveProductsTest() throws Exception {

        ProductDto product2 = ProductDto.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" phone.png")
                .discountPrice(75000.00)
                .colour("red")
                .price(95000.00)
                .materialUsed(" some light particles... ")
                .weight(100)
                .stock(false).build();

        ProductDto product3 = ProductDto.builder()
                .title(" table tennis  ")
                .description(" table tennis kit below 2 lac  ")
                .live(true)
                .imageName(" tennis.png")
                .discountPrice(45000.00)
                .colour("red")
                .price(55000.00)
                .materialUsed(" some light particles... used")
                .weight(70)
                .stock(false).build();

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(product2,product3));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(productServiceI.getAllLiveProducts(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/products/live")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void searchProductByTitleTest() throws Exception {

        String subtitle = "kit";

        ProductDto product1 = ProductDto.builder()
                .title(" cricket kit  ")
                .description(" cricket kits above 1 lac  ")
                .live(true)
                .imageName(" bat.png")
                .discountPrice(65000.00)
                .colour("skyBlue")
                .price(75000.00)
                .materialUsed(" some light particles... ")
                .weight(50)
                .stock(false).build();

        ProductDto product2 = ProductDto.builder()
                .title(" Mobile phones ")
                .description(" Mobile phones above 1 lac with blue background ")
                .live(true)
                .imageName(" phone.png")
                .discountPrice(75000.00)
                .colour("red")
                .price(95000.00)
                .materialUsed(" some light particles... ")
                .weight(100)
                .stock(false).build();

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(product1,product2));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(productServiceI.searchProductByTitle(Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/product/search/"+subtitle)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    void uploadProductImageTest() {
    }

    @Test
    void serverImageTest() {
    }

    @Test
    void createProductWithCategoryIdTest() throws Exception {

        String categoryId ="klm";
        ProductDto productDto = this.mapper.map(product, ProductDto.class);

        Mockito.when(productServiceI.createProductWithCategory(Mockito.any(),Mockito.anyString())).thenReturn(productDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/category/"+categoryId+"/product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.weight").exists());
    }

    @Test
    void updateProductWithCategoryIdTest() {
    }
}