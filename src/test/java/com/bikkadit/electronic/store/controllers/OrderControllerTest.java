package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.payloads.*;
import com.bikkadit.electronic.store.service.OrderServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceI orderService;

    @InjectMocks
    private OrderController orderController;

    private CreateOrderRequest request;
     UserDto userDto;
     OrderDto orderDto,orderDto1,orderDto2;
     OrderItemDto orderItemDto,orderItemDto1;
     ProductDto productDto,productDto1;
    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .name("mahesh")
                .email("sharma@gmail.com")
                .password("mahesh123")
                .gender("male")
                .about("Testing method for create order")
                .imageName("abc.png")
                .build();

        productDto = ProductDto.builder()
                .title("Nokia")
                .description("Phone having good battery")
                .price(40000.00)
                .discountPrice(10000.00)
                .quantity(40)
                .live(true)
                .stock(false)
                .imageName("xaz.png")
                .build();

        productDto1 = ProductDto.builder()
                .title("MI")
                .description("Phone having good HD camera")
                .price(20000.00)
                .discountPrice(18000.00)
                .quantity(10)
                .live(true)
                .stock(false)
                .imageName("anc.png")
                .build();
        orderItemDto= OrderItemDto.builder()
                .product(productDto)
                .totalPrize(134000)
                .quantity(10)
                .build();

        orderItemDto1=OrderItemDto.builder()
                .product(productDto1)
                .quantity(5)
                .totalPrize(150000)
                .build();

        List list=new ArrayList(Set.of(orderItemDto,orderItemDto1));
        orderDto= OrderDto.builder().items(list).orderAmount(3000)
                .orderDate(new Date())
                .orderStatus("Pending")
                .billingName("mahesh")
                .paymentStatus("NOT_PAID")
                .billingAddress("punjab")
                .billingPhone("78456844")
                .delieveryDate(null)
                .build();

        orderDto1= OrderDto.builder().items(list).orderAmount(30000)
                .orderDate(new Date())
                .orderStatus("Pending")
                .billingName("sonam")
                .paymentStatus("NOT_PAID")
                .billingAddress("Abb.bad")
                .billingPhone("78456844")
                .delieveryDate(null)
                .build();

        orderDto2= OrderDto.builder().items(list).orderAmount(20000)
                .orderDate(new Date())
                .orderStatus("Delivered")
                .billingName("kajal")
                .paymentStatus("PAID")
                .billingAddress("Nashik")
                .billingPhone("78456844")
                .delieveryDate(null)
                .build();

        orderDto.setCreatedBy(userDto.getCreatedBy());
        orderDto.setUpdatedBy(userDto.getUpdatedBy());
        orderDto.setIsActive(userDto.getIsActive());
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
    void createOrder() {
    }

    @Test
    void removeOrder() {
    }

    @Test
    void getAllOrdersOfUser() {
    }

    @Test
    void getAllOrders() {
    }

    @Test
    void updateOrder() {
    }
}