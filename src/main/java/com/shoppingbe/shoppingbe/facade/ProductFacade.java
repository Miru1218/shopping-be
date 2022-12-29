package com.shoppingbe.shoppingbe.facade;

import com.shoppingbe.shoppingbe.entity.Product;
import com.shoppingbe.shoppingbe.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFacade {
    private ProductService productService;

    public ProductFacade(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> getAllProducts() throws Exception {
        List<Product> products = productService.getAllProducts();
        return products;
    }

    public List<String> getCategories() throws Exception {
        List<String> categories = productService.getCategories();
        return categories;
    }

    public List<Product> getSearchCategories(String categories) throws Exception {
        List<Product> getSearchCategories = productService.getSearchCategories(categories);
        return getSearchCategories;
    }

    public List<Product> getAllSlug(String slug) throws Exception {
        List<Product> allSlug = productService.getAllSlug(slug);
        return allSlug;
    }

}
