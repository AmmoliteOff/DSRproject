package org.dsr.practice.repos;

import org.dsr.practice.models.Account;
import org.dsr.practice.models.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface  AccountRepository  extends CrudRepository<Account,Long> {
    Account findByAccountId(Long id);
    List<Account> findTopByOrderByAccountIdDesc();
}
