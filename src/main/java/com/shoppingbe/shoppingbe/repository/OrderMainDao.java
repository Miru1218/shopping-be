package com.shoppingbe.shoppingbe.repository;

import com.shoppingbe.shoppingbe.entity.OrderMain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface OrderMainDao extends CrudRepository<OrderMain, Integer> {
}
