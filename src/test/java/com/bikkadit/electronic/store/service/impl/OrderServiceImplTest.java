package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.*;
import com.bikkadit.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CreateOrderRequest;
import com.bikkadit.electronic.store.payloads.OrderDto;
import com.bikkadit.electronic.store.repositories.CartRepository;
import com.bikkadit.electronic.store.repositories.OrderRepository;
import com.bikkadit.electronic.store.repositories.UserRepository;
import com.bikkadit.electronic.store.service.OrderServiceI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

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
        user = User.builder()
                .name("Mahesh")
                .email("sharma@gmail.com")
                .password("mahesh156")
                .gender("male")
                .about("Testing method for create order")
                .imageName("abc.png")
                .build();

        product1 = Product.builder()
                .title("samsung")
                .description("Phone having good battery and also good look")
                .price(42000.00)
                .discountPrice(9000.00)
                .quantity(27)
                .live(true)
                .stock(false)
                .imageName("xyz.png")
                .build();

        product2 = Product.builder()
                .title("1+")
                .description("Phone having good HD camera and grate pic management")
                .price(60000.00)
                .discountPrice(5000.00)
                .quantity(7)
                .live(true)
                .stock(false)
                .imageName("png.png")
                .build();

        orderItem1 = OrderItem.builder()
                .product(product1)
                .totalPrize(134000)
                .quantity(10)
                .build();

        orderItem2 = OrderItem.builder()
                .product(product2)
                .quantity(5)
                .totalPrize(150000)
                .build();

        List list = new ArrayList<>();
        list.add(orderItem1);
        list.add(orderItem2);

        order = Order.builder().items(list).orderAmount(3000)
                .orderDate(new Date())
                .orderStatus("Pending")
                .billingName("mahesh")
                .paymentStatus("NOT_PAID")
                .billingAddress("haryana")
                .billingPhone("78456844")
                .delieveryDate(null)
                .build();

        order1 = Order.builder().items(list).orderAmount(30000)
                .orderDate(new Date())
                .orderStatus("Pending")
                .billingName("pankaj")
                .paymentStatus("NOT_PAID")
                .billingAddress("Ab.bad")
                .billingPhone("78456844")
                .delieveryDate(null)
                .build();

        order2 = Order.builder().items(list).orderAmount(20000)
                .orderDate(new Date())
                .orderStatus("Pending")
                .billingName("kabir")
                .paymentStatus("NOT_PAID")
                .billingAddress("punjab")
                .billingPhone("78456844")
                .delieveryDate(null)
                .build();
        order.setCreatedBy(user.getCreatedBy());
        order.setUpdatedBy(user.getUpdatedBy());
        order.setIsActive(user.getIsActive());

        List<Order> orderList = Arrays.asList(order, order1, order2);
        List<OrderDto> dtoList = orderList.stream().map(e -> mapper.map(e, OrderDto.class)).toList();

        cartItem1 = CartItem.builder()
                .product(product1)
                .quantity(9)
                .totalPrize(13400)
                .build();

        cartItem2 = CartItem.builder()
                .product(product2)
                .quantity(11)
                .totalPrize(15000)
                .build();

        List lists = new ArrayList<>();
        lists.add(cartItem1);
        lists.add(cartItem2);

        cart = Cart.builder()
                .user(user)
                .cartItem(lists)
                .build();

        cart.setCreatedBy(user.getCreatedBy());
        cart.setUpdatedBy(user.getUpdatedBy());
        cart.setIsActive(user.getIsActive());
    }

    @Test
    void createOrder() {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .userId("user123")
                .cartId("44")
                .billingAddress("haryana")
                .billingPhone("983776722")
                .billingName("Mahesh")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(cartRepository.findById(Mockito.anyString())).thenReturn(Optional.of(cart));
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        OrderDto order3 = orderService.createOrder(request);
        Mockito.verify(cartRepository, Mockito.times(1)).save(Mockito.any());
        System.out.println(order3);
        Assertions.assertNotNull(order3);
        Assertions.assertEquals(order.getBillingName(), order3.getBillingName());
        System.out.println(order3.getBillingName());
    }

    @Test
    void removeOrder() {

        String orderId = "123";
        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        orderService.removeOrder(orderId);
        Mockito.verify(orderRepository, Mockito.times(1)).delete(order);
        Assertions.assertThrows(RuntimeException.class, () -> orderService.removeOrder("55"));
    }

    @Test
    void getAllOrdersOfUser() {
        String userId = "xyz";
        List<Order> orderList = Arrays.asList(order, order1, order2);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(orderRepository.findByUser(user)).thenReturn(orderList);
        List<OrderDto> ordersOfUser = orderService.getAllOrdersOfUser(userId);
        System.out.println(orderList.size());
        System.out.println(ordersOfUser.size());
        Assertions.assertEquals(orderList.size(), ordersOfUser.size());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> orderService.getAllOrdersOfUser("abc"));
    }

    @Test
    void getAllOrders() {
        List<Order> orderList = Arrays.asList(order, order1, order2);
        Page<Order> page = new PageImpl<>(orderList);
        Mockito.when(orderRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<OrderDto> orders = orderService.getAllOrders(1, 2, "orderDate", "asc");
        System.out.println(page.getContent().size());
        System.out.println(orders.getContent().size());
        Assertions.assertEquals(page.getContent().size(), orders.getContent().size());
    }

    @Test
    void updateOrder() {
        String orderId="123";
        OrderDto orderDto= OrderDto.builder().orderAmount(12000)
                .orderDate(new Date()).
                orderStatus("Delivered")
                .billingName("Mahesh")
                .paymentStatus("PAID")
                .billingAddress("Gurugram")
                .billingPhone("454784")
                .delieveryDate(null)
                .build();


        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        OrderDto updateOrder = orderService.updateOrder(orderId,orderDto);
        System.out.println(updateOrder.getOrderStatus());
        Assertions.assertEquals("Delivered",updateOrder.getOrderStatus());
    }
}