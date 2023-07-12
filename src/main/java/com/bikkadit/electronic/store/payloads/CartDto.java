package com.bikkadit.electronic.store.payloads;

import com.bikkadit.electronic.store.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private String cartId;

    private User user;
}
