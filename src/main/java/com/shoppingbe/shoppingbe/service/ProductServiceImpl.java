package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.Product;
import com.shoppingbe.shoppingbe.repository.ProductDao;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getAllProducts() throws Exception {
        List<Product> allProducts = new ArrayList<>();
        productDao.findAll().forEach(allProducts::add);
        return allProducts;
    }
    @Override
    public List<String> getCategories() throws Exception {
        List<Product> products = new ArrayList<>();
        productDao.findAll().forEach(products::add);
        List<String> categories = products.stream().distinct().map(Product::getCategory).collect(Collectors.toList());
        return categories;
    }

    @Override
    public List<Product> getSearchCategories(String categories) throws Exception {
        String categoriesDecode = URLDecoder.decode(categories, StandardCharsets.UTF_8.name());
        List<Product> searchCategories = new ArrayList<>();
        searchCategories.add(productDao.findByCategory(categoriesDecode));
        return searchCategories;
    }

    @Override
    public List<Product> getAllSlug(String slug) throws Exception {
        String slugDecode = URLDecoder.decode(slug, StandardCharsets.UTF_8.name());
        List<Product> allSlug = new ArrayList<>();
        allSlug.add(productDao.findBySlug(slugDecode));
        return allSlug;
    }

}
