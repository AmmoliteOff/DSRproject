package org.dsr.practice.repos;

import org.dsr.practice.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByPhone(String phone);
    User findByEmail(String email);

    User findByUserId(Long id);
}