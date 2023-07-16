package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CreateOrderRequest;
import com.bikkadit.electronic.store.payloads.OrderDto;

import java.util.List;

public interface OrderServiceI {

    //create order
    OrderDto createOrder(CreateOrderRequest createOrderRequest);

    //remove order
    void removeOrder(String orderId);

    //get All orders of specific user
    List<OrderDto> getAllOrdersOfUser(String userId);

    //get All orders
    PageableResponse<OrderDto> getAllOrders(int pageNumber , int pageSize, String sortBy ,String sortDir);

    //update order method assignment
}
