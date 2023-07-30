package com.bikkadit.electronic.store.payloads;

import com.bikkadit.electronic.store.entities.Product;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto extends BaseEntityDto {


    private Integer cartItemId;

    private ProductDto product;

    private Integer quantity;

    private Integer totalPrize;

}
