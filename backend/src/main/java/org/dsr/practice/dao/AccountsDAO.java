package org.dsr.practice.dao;

import org.dsr.practice.models.Account;
import org.dsr.practice.models.User;
import org.dsr.practice.repos.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountsDAO {
    @Autowired
    AccountRepository accountRepository;

    public Account getById(Long id){
        return accountRepository.findByAccountId(id);
    }

    public Account add(String title, String description, List<User> users){
        accountRepository.save(new Account(title, description, users));
        return accountRepository.findTopByOrderByAccountIdDesc().get(0);
    }
}
