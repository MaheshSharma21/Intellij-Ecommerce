package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.OrderDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderServiceI {

    //create order
    OrderDto createOrder(OrderDto orderdto , String userId);

    //remove order
    void removeOrder(String orderId);

    //get All orders of specific user
    List<OrderDto> getAllOrders(String orderId);

    //get All orders
    PageableResponse<OrderDto> getAllOrders(int pageNumber , int pageSize, String sortBy ,String sortDir);

    //update order
}
