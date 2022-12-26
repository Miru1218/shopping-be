package com.shoppingbe.shoppingbe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "slug")
    private String slug;
    @Column(name = "category")
    private String category;
    @Column(name = "image")
    private String image;
    @Column(name = "price")
    private int price;
    @Column(name = "count_in_stock")
    private int countInStock;
    @Column(name = "brand")
    private String brand;
    @Column(name = "rating")
    private double rating;

    @Column(name = "num_reviews")
    private int numReviews;
    @Column(name = "description")
    private String description;

}
