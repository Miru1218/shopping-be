package com.shoppingbe.shoppingbe.repository;

import com.shoppingbe.shoppingbe.entity.OrderMain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface OrderMainDao extends CrudRepository<OrderMain, UUID> {
    List<OrderMain> findByUserId(Integer userId);
}
