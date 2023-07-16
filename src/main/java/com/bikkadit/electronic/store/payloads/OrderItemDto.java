package com.bikkadit.electronic.store.payloads;

import com.bikkadit.electronic.store.entities.Order;
import com.bikkadit.electronic.store.entities.Product;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto extends BaseEntityDto {

    private Integer orderItemId;

    @NotBlank(message = " orderItem quantity is required .....")
    private int quantity;

    private int totalPrize;
K
    private Product product;
}
