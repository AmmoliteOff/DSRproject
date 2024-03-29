package org.dsr.practice.repos;

import org.dsr.practice.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountRepository extends CrudRepository<Account,Long> {
    List<Account> findTopByOrderByAccountIdDesc();
    Account findByAccountId(Long id);
}
