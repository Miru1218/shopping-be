package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts() throws Exception;

    List<Product> getAllSlug(String slug) throws Exception;
}
