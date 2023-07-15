package com.bikkadit.electronic.store.payloads;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemToCartRequest {

    private String productId;
    private int quantity;

}
