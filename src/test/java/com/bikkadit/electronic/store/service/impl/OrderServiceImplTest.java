package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.*;
import com.bikkadit.electronic.store.repositories.CartRepository;
import com.bikkadit.electronic.store.repositories.OrderRepository;
import com.bikkadit.electronic.store.repositories.UserRepository;
import com.bikkadit.electronic.store.service.OrderServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderServiceI orderService;

    private User user;
    Product product1, product2;
    Cart cart;
    CartItem cartItem1, cartItem2;
    Order order, order1, order2;
    OrderItem orderItem1, orderItem2;

    @BeforeEach
    void setUp() {
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