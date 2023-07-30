package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.Cart;
import com.bikkadit.electronic.store.entities.CartItem;
import com.bikkadit.electronic.store.entities.Product;
import com.bikkadit.electronic.store.entities.User;
import com.bikkadit.electronic.store.repositories.CartItemRepository;
import com.bikkadit.electronic.store.repositories.CartRepository;
import com.bikkadit.electronic.store.repositories.ProductRepository;
import com.bikkadit.electronic.store.repositories.UserRepository;
import com.bikkadit.electronic.store.service.CartServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private CartItemRepository cartItemRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartServiceI cartService;

    User user;
    Cart cart;
    Product product,product1;
    CartItem cartItem1,cartItem2;
    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("Mahesh")
                .email("sharma@gmail.com")
                .password("mahesh123")
                .gender("male")
                .about("Testing method for cart")
                .imageName("abc.png")
                .build();

        product = Product.builder()
                .title("vivo")
                .description("Phone having good camera and speaker")
                .price(120000.00)
                .discountPrice(1500.00)
                .quantity(20)
                .live(true)
                .stock(false)
                .imageName("abc.png")
                .build();

        product1 = Product.builder()
                .title("MI")
                .description("Phone having good HD camera")
                .price(20000.00)
                .discountPrice(1500.00)
                .quantity(10)
                .live(true)
                .stock(false)
                .imageName("xyz.png")
                .build();

        cartItem1=CartItem.builder()
                .product(product)
                .quantity(10)
                .totalPrize(140000)
                .build();

        cartItem2=CartItem.builder()
                .product(product1)
                .quantity(11)
                .totalPrize(15000)
                .build();

        cart= Cart.builder()
                .user(user)
                .cartItem(List.of(cartItem1,cartItem2))
                .build();
        cart.setCreatedBy(user.getCreatedBy());
        cart.setUpdatedBy(user.getUpdatedBy());
        cart.setIsActive(user.getIsActive());
    }

    @Test
    void addItemsToCart() {
    }

    @Test
    void removeItemFromCart() {
    }

    @Test
    void clearCart() {
    }

    @Test
    void getCartByUser() {
    }
}