package com.bikkadit.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="products")
public class Product {

    @Id
    private String productId;
    @Column(name="product_title")
    private String title;
    @Column(name="product_description")
    private String description;
    @Column(name="product_price")
    private Double price;
    @Column(name="product_quantity")
    private Integer quantity;
    @Column(name="product_weight")
    private Integer weight;
    @Column(name="product_colour")
    private String colour;
    @Column(name="product_materialUsed")
    private String materialUsed;
    @Column(name="product_added_date")
    private Date addedDate;
    @Column(name="product_isLive")
    private boolean live;
    @Column(name="product_isAvailable")
    private boolean stock=true;
    @Column(name="product_discountPrice")
    private Double discountPrice;

    @Column(name="product_imageName")
    private String imageName;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="category_product")
    private Category category;
}
