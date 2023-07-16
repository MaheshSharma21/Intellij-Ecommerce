package com.bikkadit.electronic.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="orderItems")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;

    @Column(name="orderItem_quantity")
    private int  quantity;

    @Column(name="orderItem_totalPrize")
    private int totalPrize;

    //order -orderItem mapping
    @ManyToOne
    @JoinColumn(name="order_Id")
    private Order order;

    //orderItem -product mapping
    @OneToOne
    @JoinColumn(name="product_Id")
    private Product product;






}
