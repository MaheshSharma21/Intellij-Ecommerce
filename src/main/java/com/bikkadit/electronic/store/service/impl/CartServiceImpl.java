package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.entities.Cart;
import com.bikkadit.electronic.store.entities.CartItem;
import com.bikkadit.electronic.store.entities.Product;
import com.bikkadit.electronic.store.entities.User;
import com.bikkadit.electronic.store.exceptions.ResourceNotFoundException;
import com.bikkadit.electronic.store.payloads.AddItemToCartRequest;
import com.bikkadit.electronic.store.payloads.CartDto;
import com.bikkadit.electronic.store.repositories.CartRepository;
import com.bikkadit.electronic.store.repositories.ProductRepository;
import com.bikkadit.electronic.store.repositories.UserRepository;
import com.bikkadit.electronic.store.service.CartServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CartServiceImpl implements CartServiceI {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemsToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();

        //fetch the product
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with this Id" + productId));

        //fetch the User
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(" User not found with this userId" + userId));

       Cart cart = null;

        try {
            cart = cartRepo.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedDate(LocalDate.now());
        }

        //perform cart operation
        //if cart Items already present then update
        //AtomicReference is a class that object reference may be updated automatically because directly we cannot update our variable n map
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> cartItem = cart.getCartItem();
        List<CartItem> updatedItems = cartItem.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {

                //item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrize(quantity * product.getPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        cart.setCartItem(updatedItems);

        //create cartItems
        if (!updated.get()) {
            CartItem cartItems = CartItem.builder()
                    .quantity(quantity)
                    .cart(cart)
                    .totalPrize(quantity * product.getPrice())
                    .product(product).build();

            cart.getCartItem().add(cartItems);
        }
        cart.setUser(user);
        Cart updatedcart = cartRepo.save(cart);
        return mapper.map(updatedcart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, Integer cartItemId) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
