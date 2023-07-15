package com.bikkadit.electronic.store.payloads;

import com.bikkadit.electronic.store.entities.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class CartItemDto extends BaseEntityDto {


    private int cartItemId;

    private ProductDto product;

    @NotBlank(message = " cart item quantity must be filled !!!  ")
    private int quantity;

    private Double totalPrize;

}
