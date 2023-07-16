package com.bikkadit.electronic.store.entities;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;

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
    private User user;

}

