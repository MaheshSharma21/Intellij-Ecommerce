package com.bikkadit.electronic.store.payloads;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {

    private String userId;
    private String cartId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private int orderAmount;
    @NotBlank(message = "Billing Address is required !!")
    private String billingAddress;
    @NotBlank(message = "Billing Phone is required !!")
    private String billingPhone;
    @NotBlank(message = "Billing Name is required !!")
    private String billingName;

}
