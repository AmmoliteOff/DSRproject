package org.dsr.practice.repos;

import org.dsr.practice.models.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account,Long> {
    List<Account> findTopByOrderByAccountIdDesc();
    Account findByAccountId(Long id);
}
