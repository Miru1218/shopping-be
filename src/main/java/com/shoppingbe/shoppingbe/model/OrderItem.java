package com.shoppingbe.shoppingbe.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
    private int id;
    private String name;
    private String slug;
    private String category;
    private String image;
    private int price;
    private int countInStock;
    private String brand;
    private Double rating;
    private String description;
    private Integer numReviews;
    private int quantity;
}
