package com.shoppingbe.shoppingbe.repository;

import com.shoppingbe.shoppingbe.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface ProductDao extends CrudRepository<Product, Integer> {
    Product findBySlug(String slug);
}
