package com.shoppingbe.shoppingbe.facade;

import com.shoppingbe.shoppingbe.entity.Product;
import com.shoppingbe.shoppingbe.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Product> getAllSlug(String slug) throws Exception {
        List<Product> allSlug = productService.getAllSlug(slug);
        return allSlug;
    }

    public Map<String, Object> getProductsByCategory(String category) throws Exception {
        //        List<Product> productList;
        //        if ("all".equalsIgnoreCase(category)) {
        //            productList = productService.getAllProducts();
        //        } else {
        //            productList = productService.getProductsByCategory
        //            (category);
        //        }
        List<Product> productList = "all".equalsIgnoreCase(category) ? productService.getAllProducts() :
                productService.getProductsByCategory(category);

        Map<String, Object> result = new HashMap<>();
        result.put("countProduct", productList.size());
        result.put("page", "1");
        result.put("pages", 1);
        result.put("products", productList);
        return result;
    }

}
