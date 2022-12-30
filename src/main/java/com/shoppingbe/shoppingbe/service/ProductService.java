package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts() throws Exception;

    List<String> getCategories() throws Exception;

    List<Product> getAllSlug(String slug) throws Exception;

    List<Product> getProductsByCategory(String category) throws Exception;
}
