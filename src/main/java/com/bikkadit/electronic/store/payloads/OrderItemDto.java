package com.bikkadit.electronic.store.payloads;

import com.bikkadit.electronic.store.entities.Order;
import com.bikkadit.electronic.store.entities.Product;
import lombok.*;

import javax.persistence.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Integer orderItemId;
    private int quantity;
    private int totalPrize;

    private Product product;
}
