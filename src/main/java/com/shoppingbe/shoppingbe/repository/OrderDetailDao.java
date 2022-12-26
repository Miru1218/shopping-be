package com.shoppingbe.shoppingbe.repository;

import com.shoppingbe.shoppingbe.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface OrderDetailDao extends PagingAndSortingRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrderId(int orderId);
}
