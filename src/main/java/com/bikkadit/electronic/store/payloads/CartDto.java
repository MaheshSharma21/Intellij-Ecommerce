package com.bikkadit.electronic.store.payloads;

import com.bikkadit.electronic.store.entities.CartItem;
import com.bikkadit.electronic.store.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto extends BaseEntityDto {

    private String cartId;

    private UserDto user;

    private List<CartItemDto> cartItem = new ArrayList<>();
}
