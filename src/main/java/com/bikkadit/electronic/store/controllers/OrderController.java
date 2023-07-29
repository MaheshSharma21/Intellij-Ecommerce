package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.helper.PageableResponse;
import com.bikkadit.electronic.store.payloads.CreateOrderRequest;
import com.bikkadit.electronic.store.payloads.OrderDto;
import com.bikkadit.electronic.store.payloads.ProductDto;
import com.bikkadit.electronic.store.service.OrderServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class OrderController {

    @Autowired
    private OrderServiceI orderServiceI;

    /**
     * @author Mahesh Sharma
     * @apiNote This api is used to create order
     * @param orderDto
     * @return
     */
    @PostMapping("/order")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest orderDto) {
        log.info("Request starting for service layer to create order");
        OrderDto order = this.orderServiceI.createOrder(orderDto);
        log.info("Request completed for service layer to create order");
        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }


    /**
     * @author Mahesh Sharma
     * @apiNote This api is used to remove Order
     * @param orderId
     * @return
     */
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {
        log.info("Request starting for service layer to remove Order with orderId :{}", orderId);
        this.orderServiceI.removeOrder(orderId);
        ApiResponse response = ApiResponse.builder().message(AppConstant.ORDER_REMOVE).success(true).Status(HttpStatus.OK).build();
        log.info("Request completed for service layer to  remove order with orderId :{}", orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * @author Mahesh Sharma
     * @apiNote This api is used to getAllOrdersOfUser
     * @param userId
     * @return
     */
    @GetMapping("/order/{userId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersOfUser(@PathVariable String userId) {
        log.info("Request starting for service layer to get All Orders Of User  with userId :{}", userId);
        List<OrderDto> allOrdersOfUser = this.orderServiceI.getAllOrdersOfUser(userId);
        log.info("Request completed for service layer to  get All Orders Of User with userId :{}", userId);
        return new ResponseEntity<>(allOrdersOfUser, HttpStatus.OK);

    }

    /**
     * @author Mahesh Sharma
     * @apiNote This api is used to getAllOrders
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */

    @GetMapping("/orders")
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrders(
            @RequestParam(value = AppConstant.PAGE_NUMBER, defaultValue = AppConstant.PAGE_NUMBER_DEFAULT_VALUE, required = false) int pageNumber,
            @RequestParam(value = AppConstant.PAGE_SIZE, defaultValue = AppConstant.PAGE_SIZE_DEFAULT_VALUE, required = false) int pageSize,
            @RequestParam(value = AppConstant.SORT_BY, defaultValue = AppConstant.SORT_BY_DEFAULT_VALUE_ORDER ,required = false) String sortBy,
            @RequestParam(value = AppConstant.SORT_DIR, defaultValue = AppConstant.SORT_DIR_DEFAULT_VALUE, required = false) String sortDir
    ) {
        log.info("Request starting for service layer to  get All Orders  ");
        PageableResponse<OrderDto> allOrders = this.orderServiceI.getAllOrders(pageNumber, pageSize, sortBy, sortDir);
        log.info("Request completed for service layer to  get All Orders ");
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    /**
     *  @author Mahesh Sharma
     *  @apiNote This api is used to update order
     * @param orderId
     * @param orderDto
     * @return
     */
    @PutMapping("/order/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable String orderId ,@RequestBody OrderDto orderDto){
        log.info("Request initialized for updating  order info for orderId  :{}",orderId);
        OrderDto orderDto1 = orderServiceI.updateOrder(orderId, orderDto);
        log.info("Request completed for updating  order info for orderId  :{}",orderId);
        return new ResponseEntity<>(orderDto1,HttpStatus.OK);
    }
}
