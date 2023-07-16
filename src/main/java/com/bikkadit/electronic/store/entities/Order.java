package com.bikkadit.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseEntity {

    @Id
    private String orderId;
    @Column(name = "order_status")
    private String orderStatus;                    //PENDING,DISPATCHED,DELIVERED   OR USE ENUM
    @Column(name = "payment_status")
    private String paymentStatus;                  //NOT PAID,PAID OR ENUM OR BOOLEAN =FALSE=>NOT PAID,TRUE =PAID
    @Column(name = "order_amount")
    private int orderAmount;
    @Column(name = "billing_address")
    private String billingAddress;
    @Column(name = "billing_phone")
    private String billingPhone;
    @Column(name = "billing_name")
    private String billingName;
    @Column(name = "order_date")
    private Date orderDate;
    private Date delieveryDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_Id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "order_OrderItems")
    private List<OrderItem> items = new ArrayList<>();

}

