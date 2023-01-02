package com.shoppingbe.shoppingbe.repository;

import com.shoppingbe.shoppingbe.entity.OrderMain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface OrderMainDao extends CrudRepository<OrderMain, Integer> {
    List<OrderMain> findByUserId(int userId);
}
