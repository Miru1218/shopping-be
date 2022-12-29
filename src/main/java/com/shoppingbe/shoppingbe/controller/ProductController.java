package com.shoppingbe.shoppingbe.controller;

import com.shoppingbe.shoppingbe.entity.Product;
import com.shoppingbe.shoppingbe.facade.ProductFacade;
import com.shoppingbe.shoppingbe.repository.ProductDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/product")
@Tag(name = "ProductController")
public class ProductController {
    @Resource(name = "productDao")
    ProductDao productDao;

    @Resource(name = "productFacade")
    ProductFacade productFacade;

    @Operation(summary = "All Products")
    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Product>> getAllProducts() throws Exception {
        List<Product> products = productFacade.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "categories")
    @GetMapping(value = "/products/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<String>> getCategories() throws Exception {
        List<String> categories = productFacade.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Operation(summary = "All Products")
    @GetMapping(value = "/products/category/{category}")
    @ResponseBody
    public ResponseEntity<List<Product>> getSearchCategories(@PathVariable("category") String category) throws Exception {
        List<Product> searchCategories = productFacade.getSearchCategories(category);
        return new ResponseEntity<>(searchCategories, HttpStatus.OK);
    }

    @Operation(summary = "All slug")
    @GetMapping(value = "/products/{slug}")
    @ResponseBody
    public ResponseEntity<List<Product>> getSlug(@PathVariable("slug") String slug) throws Exception {
        List<Product> allSlug = productFacade.getAllSlug(slug);
        return new ResponseEntity<>(allSlug, HttpStatus.OK);
    }
}

