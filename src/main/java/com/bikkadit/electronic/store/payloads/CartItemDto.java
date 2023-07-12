package com.bikkadit.electronic.store.payloads;

import com.bikkadit.electronic.store.entities.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
public class CartItemDto {


    private int cartItemId;

    private ProductDto product;

    private int quantity;

    private int totalPrize;

}
