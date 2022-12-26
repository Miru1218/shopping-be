package com.shoppingbe.shoppingbe.controller;

import com.shoppingbe.shoppingbe.entity.Product;
import com.shoppingbe.shoppingbe.repository.ProductDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
@Tag(name = "ProductController")
public class ProductController {
    @Autowired
    ProductDao productDao;

    @Operation(summary = "All Products")
    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        productDao.findAll().forEach(allProducts::add);
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @Operation(summary = "All slug")
    @GetMapping(value = "/products/{slug}")
    @ResponseBody
    public ResponseEntity<?> getSlug(@PathVariable("slug") String slug) throws Exception {
        String slugDecode = URLDecoder.decode(slug, StandardCharsets.UTF_8.name());
        List<Product> allSlug = new ArrayList<>();
        allSlug.add(productDao.findBySlug(slugDecode));
        return new ResponseEntity<>(allSlug, HttpStatus.OK);
    }
}

