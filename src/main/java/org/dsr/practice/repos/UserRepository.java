package org.dsr.practice.repos;

import org.springframework.data.repository.CrudRepository;
import org.dsr.practice.models.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Long> {
    List<User> findByPhone(String phone);
    User findByEmail(String email);
}