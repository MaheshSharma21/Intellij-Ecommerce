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

   // @Max(value = 100,message = " You can add description up to 100 length ")
   @Size(min=25 ,max=100,message = "Product description must contains at least 25 characters and maximum 100 characters")
    private String description;

    @NotNull(message = "product price is required ....")
    private Double price;



    @NotNull(message = " please enter how much product quantity you want ")
    private Integer quantity;

    @NotNull(message = " you can buy product up to  50 kg weight")
    private Integer weight;

    @NotBlank(message = " colours available for product  are of any type ... ")
    @Size(max = 10 ,message = " colours name above 10 characters  are not acceptable ")
    private String colour;

    @NotBlank(message = "  please enter valid Material used for product  ")
   // @Size(max = 20 ,message = " material name above 10 characters  is required  ")
    private String materialUsed;


    private Date addedDate;

   // @NotBlank(message = " product live field is mandatory to fill ....")
   // @Size(min =4,max =5 ,message = " product live size  exists  within 4-5 range ")
    private boolean live;

    private boolean stock = true;


    @NotNull(message = " enter some rela vent discount price")
    private Double discountPrice;


    @NotBlank(message = "ImageName is required everytime...")
    private String imageName;


    private CategoryDto category;

}
