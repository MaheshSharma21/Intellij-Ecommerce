package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.payloads.*;
import com.bikkadit.electronic.store.service.CartServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {
    private UserDto userDto;

    @MockBean
    private CartServiceI cartService;

    @InjectMocks
    private CartController cartController;

    @Autowired
    private MockMvc mockMvc;

    CartDto cartDto;
    CartItemDto cartItemDto,  cartItemDto1;
    ProductDto productDto,productDto1;

    @BeforeEach
    void setUp() {



        userDto = UserDto.builder()
                .name("Mahesh")
                .email("sharma@gmail.com")
                .password("Mahesh123")
                .gender("male")
                .about("Testing method for create")
                .imageName("abc.png")
                .build();

        productDto = ProductDto.builder()
                .title("Samsung")
                .description("Phone having good camera")
                .price(120000.00)
                .discountPrice(10000.00)
                .quantity(40)
                .live(true)
                .stock(false)
                .imageName("abc.png")
                .build();

        productDto1 = ProductDto.builder()
                .title("MI")
                .description("Phone with good HD camera and hd view")
                .price(20000.00)
                .discountPrice(18000.00)
                .quantity(10)
                .live(true)
                .stock(false)
                .imageName("xyz.png")
                .build();

        cartItemDto= CartItemDto.builder()
                .product(productDto)
                .quantity(10)
                .totalPrize(13400)
                .build();

        cartItemDto1=CartItemDto.builder()
                .product(productDto1)
                .quantity(11)
                .totalPrize(15000)
                .build();


        cartDto= CartDto.builder()
                .user(userDto)
                .cartItem(List.of(cartItemDto,cartItemDto1))
                .build();
        cartDto.setCreatedBy(userDto.getCreatedBy());
        cartDto.setUpdatedBy(userDto.getUpdatedBy());
        cartDto.setIsActive(userDto.getIsActive());

        AddItemToCartRequest request = AddItemToCartRequest.builder().productId("12345").quantity(10).build();


    }
    private String convertObjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Test
    void addItemsToCart() throws Exception {
        String userId="123";
        Mockito.when(cartService.addItemsToCart(Mockito.anyString(),Mockito.any())).thenReturn(cartDto);
        //actual request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/cart/add/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(cartDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(("$.user")).exists());
    }

    @Test
    void removeItemFromCart() {
        String userId= "12345";
        Integer cartItemId=11;
        cartService.removeItemFromCart(userId,cartItemId);
        Mockito.verify(cartService,Mockito.times(1)).removeItemFromCart(userId, cartItemId);
    }

    @Test
    void clearCart() {
        String userId="abc";
        cartService.clearCart(userId);
        Mockito.verify(cartService,Mockito.times(1)).clearCart(userId);
    }

    @Test
    void getCart() throws Exception {
        String userId="abc";
        Mockito.when(cartService.getCartByUser(Mockito.anyString())).thenReturn(cartDto);
        //actual request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/cart/"+userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(cartDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user").exists());
    }
}