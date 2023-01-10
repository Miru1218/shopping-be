package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ProductService {

    List<Product> getAllProducts() throws Exception;

    List<String> getCategories() throws Exception;

    List<Product> getAllSlug(String slug) throws Exception;

    List<Product> getProductsByCategory(String category) throws Exception;

    Page<Product> findAllWithSpecification(String name, String category, String price, String rating,
                                           PageRequest pageRequest) throws UnsupportedEncodingException;
}
