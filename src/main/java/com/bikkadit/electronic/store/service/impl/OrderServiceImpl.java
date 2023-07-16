package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.*;
import com.bikkadit.electronic.store.exceptions.BadRequestApiException;
import com.bikkadit.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.General;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CreateOrderRequest;
import com.bikkadit.electronic.store.payloads.OrderDto;
import com.bikkadit.electronic.store.repositories.CartRepository;
import com.bikkadit.electronic.store.repositories.OrderRepository;
import com.bikkadit.electronic.store.repositories.UserRepository;
import com.bikkadit.electronic.store.service.OrderServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
@Service
@Slf4j
public class OrderServiceImpl implements OrderServiceI {
   @Autowired
    private UserRepository userRepo;
   @Autowired
   private OrderRepository orderRepo;
   @Autowired
   private CartRepository cartRepo;

   @Autowired
   private ModelMapper mapper;

    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        log.info("Request Starting  to create order ");
        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();
        //user fetch
        log.info("Request Starting  to find the user  with userId : {}", userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MSG));
        //cart fetch
        log.info(" Request Starting  to find the cart with cartId : {}", cartId);
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CART_NOT_FOUND));
        List<CartItem> cartItem = cart.getCartItem();
        if(cartItem.size()<=0){
            throw new BadRequestApiException(AppConstant.CART_INVALID_ITEMS);
        }
        //other checks
        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingAddress(orderDto.getBillingAddress())
                .billingPhone(orderDto.getBillingPhone())
                .orderDate(new Date())
                .delieveryDate(null)
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .paymentStatus(orderDto.getPaymentStatus())
                .user(user).build();

        //order Items ,amount
        AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(0);
        List<OrderItem> orderItems = cartItem.stream().map(cartItems -> {
            //CartItem -> OrderItem
            OrderItem orderItm= OrderItem.builder()
                    .quantity(cartItems.getQuantity())
                    .product(cartItems.getProduct())
                    .totalPrize((int) (cartItems.getCartItemId()*cartItems.getProduct().getDiscountPrice()))
                    .order(order)
                    .build();
            atomicReference.set(atomicReference.get()+orderItm.getTotalPrize());
            return new OrderItem();
        }).collect(Collectors.toList());
        order.setItems(orderItems);
        order.setOrderAmount(atomicReference.get());

        cart.getCartItem().clear();
        cartRepo.save(cart);
        Order save = orderRepo.save(order);
        log.info("Request completed  to create the order ");
        return mapper.map(save,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        log.info(" Request Starting  to remove the order with orderId : {}", orderId);
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.ORDER_NOT_FOUND));
    orderRepo.delete(order);
        log.info(" Request completed  to remove the order with orderId : {}", orderId);
    }

    @Override
    public List<OrderDto> getAllOrdersOfUser(String userId) {
        log.info("Request Starting  to get All Orders of  user  with userId : {}", userId);
        //user fetch
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MSG));
        List<Order> orders = orderRepo.findByUser(user);
        List<OrderDto> orderList = orders.stream().map((order) -> mapper.map(order, OrderDto.class)).collect(Collectors.toList());
        log.info("Request completed  to get All Orders of  user  with userId : {}", userId);
        return orderList;
    }

    @Override
    public PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Request Starting  to get All Orders ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable =PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = orderRepo.findAll(pageable);
        log.info("Request completed  to get All Orders ");
        return General.getPageableResponse(page,OrderDto.class);
    }
}
