package com.bikkadit.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
@Entity
@Table(name="cart_Items")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    //mapping product
    @JoinColumn(name = "product_Id")
    @OneToOne
    private Product product;

    @Column(name = "product_quantity")
    private int quantity;

    @Column(name = "product_totalPrize")
    private int totalPrize;

    //mapping cart
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart ;
}
