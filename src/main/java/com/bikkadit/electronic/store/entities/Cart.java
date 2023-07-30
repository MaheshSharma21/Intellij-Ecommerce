package com.bikkadit.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends  BaseEntity{
    @Id
    private String cartId;

    @JoinColumn(name = "user_Id")
    @OneToOne
    private User user;

    //mapping cart Item
    //use OrphanRemoval =true if we want to delete all cartItems from cart or to clear cartK
    @OneToMany(mappedBy = "cart",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<CartItem> cartItem = new ArrayList<>();



}
