package com.shoppingbe.shoppingbe.repository;

import com.shoppingbe.shoppingbe.entity.ShippingAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ShippingAddressDao extends CrudRepository<ShippingAddress, Integer> {

    List<ShippingAddress> findByOrderId(UUID orderId);
}
