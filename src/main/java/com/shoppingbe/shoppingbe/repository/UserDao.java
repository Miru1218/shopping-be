package com.shoppingbe.shoppingbe.repository;

import com.shoppingbe.shoppingbe.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserDao extends CrudRepository<User, Integer> {
    User findByAccount(String account);
}
