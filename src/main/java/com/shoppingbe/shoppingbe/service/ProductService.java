package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts() throws Exception;

    List<String> getCategories() throws Exception;

    List<Product> getSearchCategories(String categories) throws Exception;

    List<Product> getAllSlug(String slug) throws Exception;
}
