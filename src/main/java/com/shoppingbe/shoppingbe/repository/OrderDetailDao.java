package com.shoppingbe.shoppingbe.repository;

import com.shoppingbe.shoppingbe.entity.OrderDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//實現介面及介面中的所有方法不對外暴露，從而限制訪問。
@RepositoryRestResource(exported = false)
//接口继承于 CrudRepository 接口，拥有CrudRepository 接口的所有方法， 并新增两个功能：分页和排序。
public interface OrderDetailDao extends PagingAndSortingRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrderId(int orderId);
}
