package com.bikkadit.electronic.store.payloads;

import lombok.*;


import javax.validation.constraints.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private String productId;

    @Size(min=5 ,max=20,message = "Product title must contains at least 5 characters and max to max 20 characters")
    private String title;

    @Max(value = 50,message = " You can add description up to 50 length ")
    private String description;

    @NotNull(message = "product price is required ....")
    private Integer price;

    @NotNull(message = " please enter how much product quantity you want ")
    private Integer quantity;

    @NotNull(message = " you can buy product up to  50kg weight")
    private Integer weight;

    @NotBlank(message = " colours available for product are Red , skyBlue , pink , Blue ")
    @Size(max = 10 ,message = " colours name above 10 characters  are not acceptable ")
    private String colour;

    @NotBlank(message = "  please enter valid Material used for product  ")
    @Size(max = 10 ,message = " material name above 10 characters  is required  ")
    private String materialUsed;



    @NotBlank(message = " valid materials are required !! ")
    @Size(max = 15 ,message = " materials name  above 15 characters  cannot use ")
    private String materialUsed;

    private Date addedDate;

    @NotBlank(message = " product live field is mandatory to fill ....")
    @Size(min =4,max =5 ,message = " product live size  exists  within 4-5 range ")
    private boolean live;

    private boolean stock = true;

}
