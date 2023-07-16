package com.bikkadit.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseEntity {

    @Id
    private String orderId;

    private String orderStatus;                    //PENDING,DISPATCHED,DELIVERED   OR USE ENUM

    private String paymentStatus;                  //NOT PAID,PAID OR ENUM OR BOOLEAN =FALSE=>NOT PAID,TRUE =PAID

    private int orderAmount;
    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderDate;
    private Date delieveryDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_Id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<OrderItem> items = new ArrayList<>();

}

