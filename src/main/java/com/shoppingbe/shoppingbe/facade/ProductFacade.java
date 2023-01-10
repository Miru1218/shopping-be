package com.shoppingbe.shoppingbe.facade;

import com.shoppingbe.shoppingbe.entity.Product;
import com.shoppingbe.shoppingbe.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Map<String, Object> getProductsByCategory(String page, String name, String category, String price,
                                                     String rating, String order) throws Exception {

        Sort sort = Sort.by("newest".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(Integer.parseInt(page), 3, sort);
        Page<Product> productPage = productService.findAllWithSpecification(name, category, price, rating, pageRequest);
        List<Product> productList = productPage.getContent();

        Map<String, Object> result = new HashMap<>();
        result.put("countProduct", productList.size());
        result.put("page", page);
        result.put("pages", productPage.getTotalPages());
        result.put("products", productList);
        return result;
    }

}
