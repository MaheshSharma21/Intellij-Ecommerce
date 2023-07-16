package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CreateOrderRequest;
import com.bikkadit.electronic.store.payloads.OrderDto;
import com.bikkadit.electronic.store.payloads.ProductDto;
import com.bikkadit.electronic.store.service.OrderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderServiceI orderServiceI;


    @PostMapping("order")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest orderDto) {
        OrderDto order = this.orderServiceI.createOrder(orderDto);
        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {
        this.orderServiceI.removeOrder(orderId);
        ApiResponse response = ApiResponse.builder().message(" order is removed").success(true).Status(HttpStatus.OK).build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/order/{userId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersOfUser(@PathVariable String userId) {
        List<OrderDto> allOrdersOfUser = this.orderServiceI.getAllOrdersOfUser(userId);
        return new ResponseEntity<>(allOrdersOfUser, HttpStatus.OK);

    }

    @GetMapping("orders")
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrders(
            @RequestParam(value = AppConstant.PAGE_NUMBER, defaultValue = AppConstant.PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = AppConstant.PAGE_SIZE, defaultValue = AppConstant.PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @RequestParam(value = AppConstant.SORT_BY, defaultValue = "......orderedDate", required = false) String sortBy,
            @RequestParam(value = AppConstant.SORT_DIR, defaultValue = AppConstant.SORT_DIR_DEFAULT_VALUE, required = false) String sortDir
    ) {
        PageableResponse<OrderDto> allOrders = this.orderServiceI.getAllOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }
}
