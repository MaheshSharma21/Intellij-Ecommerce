package com.bikkadit.electronic.store.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {

    @Id
    private String categoryId;

    @Column(name = "category_title")
    private String title;

    @Column(name = "category_description")
    private String description;

    private String coverImage;
}
