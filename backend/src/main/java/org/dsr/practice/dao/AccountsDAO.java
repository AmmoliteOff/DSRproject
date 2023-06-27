package org.dsr.practice.dao;

import org.dsr.practice.models.Account;
import org.dsr.practice.repos.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountsDAO {
    AccountRepository accountRepository;

    public AccountsDAO(@Autowired AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account Add(Account account){
        accountRepository.save(account);
        return accountRepository.findTopByOrderByAccountIdDesc().get(0);
    }

    public void Update(Account account){
        accountRepository.save(account);
    }

    public Account FindById(Long id){
        return accountRepository.findByAccountId(id);
    }
}
