package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.payloads.AddItemToCartRequest;
import com.bikkadit.electronic.store.payloads.CartDto;

public interface CartServiceI {

    //add items to cart
    // case 1 : cart for user is not available then we will create the cart and then add items to cart
    //case 2 : cart available then we will add items to cart

    CartDto addItemsToCart(String userId , AddItemToCartRequest request );

    //remove Item from Cart
    void removeItemFromCart(String userId ,Integer cartItemId);

    //remove All items from cart
    void clearCart(String userId);

    //to get cart of specific user
    CartDto getCartByUser(String userId);
}
