package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.*;
import com.bikkadit.electronic.store.exceptions.BadRequestApiException;
import com.bikkadit.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.OrderDto;
import com.bikkadit.electronic.store.repositories.CartRepository;
import com.bikkadit.electronic.store.repositories.OrderRepository;
import com.bikkadit.electronic.store.repositories.UserRepository;
import com.bikkadit.electronic.store.service.OrderServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    public OrderDto createOrder(OrderDto orderdto, String userId ,String cartId) {

        //user fetch
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MSG));

        //cart fetch
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException(" cart not found with cartId"));

        List<CartItem> cartItem = cart.getCartItem();
        if(cartItem.size()<=0){
            throw new BadRequestApiException(" invalid number of items present iun cart ...");
        }

        //other checks
        Order order = Order.builder()
                .billingName(orderdto.getBillingName())
                .billingAddress(orderdto.getBillingAddress())
                .billingPhone(orderdto.getBillingPhone())
                .orderDate(new Date())
                .delieveryDate(orderdto.getDelieveryDate())
                .orderStatus(orderdto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .paymentStatus(orderdto.getPaymentStatus())
                .user(user).build();


        //order Items ,amount
        AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(0);
        List<OrderItem> collect = cartItem.stream().map(cartItems -> {

            //CartItem -> OrderItem

            OrderItem orderItems= OrderItem.builder()
                    .quantity(cartItems.getQuantity())
                    .product(cartItems.getProduct())
                    .totalPrize(cartItems.getQuantity() * cartItems.getProduct().getDiscountPrice())
                    .order(order)
                    .build();
            atomicReference.set(atomicReference.get()+orderItem.getTotalPrize());
            return new OrderItem();
        }).collect(Collectors.toList());
        order.setItems(orderItems);
        order.setOrderAmount(atomicReference.get());

        //
        cart.getCartItem().clear();
        cartRepo.save(cart);
        Order save = orderRepo.save(order);

        return mapper.map(save,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

    }

    @Override
    public List<OrderDto> getAllOrders(String orderId) {
        return null;
    }

    @Override
    public PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }
}
