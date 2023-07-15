package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.payloads.AddItemToCartRequest;
import com.bikkadit.electronic.store.payloads.CartDto;
import com.bikkadit.electronic.store.service.CartServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartServiceI cartServiceI;

    //add cart items to cart

    /**
     * @param request
     * @param userId
     * @return
     * @apiNote This api is used to Add items to  cart
     * @author Mahesh Sharma
     */
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemsToCart(@RequestBody AddItemToCartRequest request, @PathVariable String userId) {

        CartDto cartDto = cartServiceI.addItemsToCart(userId, request);

        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @param itemId
     * @return
     * @apiNote This api is used to remove items from  cart
     * @author Mahesh Sharma
     */
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId, @PathVariable Integer itemId) {

        cartServiceI.removeItemFromCart(userId, itemId);
        ApiResponse response = ApiResponse.builder().message(" item removed successfully ").success(true).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @apiNote This api is used to clear  cart
     * @author Mahesh Sharma
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId) {

        cartServiceI.clearCart(userId);
        ApiResponse response = ApiResponse.builder().message("  Now cart is blank ").success(true).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @apiNote This api is used to get user specific  cart
     * @author Mahesh Sharma
     */
    @PostMapping("/cart/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        CartDto cartDto = cartServiceI.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}
