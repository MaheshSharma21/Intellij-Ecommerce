package com.bikkadit.electronic.store.controllers;

import com.bikkadit.electronic.store.helper.ApiResponse;
import com.bikkadit.electronic.store.helper.AppConstant;
import com.bikkadit.electronic.store.payloads.AddItemToCartRequest;
import com.bikkadit.electronic.store.payloads.CartDto;
import com.bikkadit.electronic.store.service.CartServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class CartController {

    @Autowired
    private CartServiceI cartServiceI;

    /**
     * @param request
     * @param userId
     * @return
     * @apiNote This api is used to Add items to  cart
     * @author Mahesh Sharma
     */
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemsToCart(@RequestBody AddItemToCartRequest request, @PathVariable String userId) {
        log.info("Request started for service layer to add items to cart in specific user cart with userId : {}",userId);
        CartDto cartDto = cartServiceI.addItemsToCart(userId, request);
        log.info("Request completed for service layer to add items to cart in specific user cart with userId : {}",userId);
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
        log.info("Request started for service layer to remove items from specific user cart with userId : {}",userId + " and with itemId : {}"+itemId);
        cartServiceI.removeItemFromCart(userId, itemId);
        ApiResponse response = ApiResponse.builder().message(AppConstant.ITEM_REMOVE).success(true).build();
        log.info("Request Completed for service layer to remove items from specific user cart with userId : {}",userId + " and with itemId : {}"+itemId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @apiNote This api is used to clear  cart
     * @author Mahesh Sharma
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart( @PathVariable String userId ){
        log.info("Request started for service layer to clear specific user cart with userId : {}",userId);
        cartServiceI.clearCart(userId );
        ApiResponse response = ApiResponse.builder().message(AppConstant.CART_BLANK).success(true).build();
        log.info("Request completed for service layer to clear specific user cart with userId : {}",userId);
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
        log.info("Request started for service layer to get K specific user cart with userId : {}",userId);
        CartDto cartDto = cartServiceI.getCartByUser(userId);
        log.info("Request completed for service layer to get K specific user cart with userId : {}",userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}
