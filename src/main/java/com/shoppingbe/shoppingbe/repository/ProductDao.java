package com.shoppingbe.shoppingbe.repository;

import com.shoppingbe.shoppingbe.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ProductDao extends CrudRepository<Product, Integer> {
    List<Product> findBySlug(String slug);

    List<Product> findByCategory(String category);
}
