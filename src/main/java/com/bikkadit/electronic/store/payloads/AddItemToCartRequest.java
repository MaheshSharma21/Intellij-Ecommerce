package com.bikkadit.electronic.store.payloads;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemToCartRequest {

    private String productId;
   // @NotBlank(message = "cartItem Quantity is mandatory ....")
    private int quantity;

}
