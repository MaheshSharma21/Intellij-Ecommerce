package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.payloads.AddItemToCartRequest;
import com.bikkadit.electronic.store.payloads.CartDto;
import com.bikkadit.electronic.store.repositories.ProductRepository;
import com.bikkadit.electronic.store.repositories.UserRepository;
import com.bikkadit.electronic.store.service.CartServiceI;
import org.springframework.beans.factory.annotation.Autowired;

public class CartServiceImpl extends CartServiceI {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private cartRepository cartRepo;

    @Override
    public CartDto addItemsToCart(String userId, AddItemToCartRequest request) {
        return null;
    }

    @Override
    public void removeItemFromCart(String userId, Integer cartItemId) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
