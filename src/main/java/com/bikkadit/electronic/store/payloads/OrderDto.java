package com.bikkadit.electronic.store.payloads;

import com.bikkadit.electronic.store.entities.OrderItem;
import com.bikkadit.electronic.store.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String orderId;
    private String orderStatus;
    private String paymentStatus;
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate;
    private Date delieveryDate;

    private UserDto user;
    private List<OrderItemDto> items = new ArrayList<>();
}
