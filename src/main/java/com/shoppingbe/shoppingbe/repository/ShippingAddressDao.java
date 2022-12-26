package com.shoppingbe.shoppingbe.repository;

import com.shoppingbe.shoppingbe.entity.ShippingAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ShippingAddressDao extends CrudRepository<ShippingAddress, Integer> {

    List<ShippingAddress> findByOrderId(int orderId);
}
