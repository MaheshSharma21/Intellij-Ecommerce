package com.bikkadit.electronic.store.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddItemToCartRequest {

    private Integer productId;
    private Integer quantity;

}
