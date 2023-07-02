package org.dsr.practice.services;

import org.dsr.practice.models.Account;
import org.dsr.practice.models.User;
import org.dsr.practice.repos.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class  AccountsService {

    private AccountRepository accountRepository;
    private UsersService usersService;

    public AccountsService(@Autowired AccountRepository accountRepository,
                           @Autowired UsersService usersService) {
        this.accountRepository = accountRepository;
        this.usersService = usersService;
    }

    public Account getAccount(Long id) {

        return accountRepository.findByAccountId(id);
    }

    public boolean createAccount(String title, String description, String[] users) {
        Set<User> usersIn = new HashSet<User>();
        try {
            for (String str : users) {
                var usr = usersService.getUser(Long.parseLong(str));
                usersIn.add(usr);
            }
            Account acc = new Account(title, description, usersIn);
            var account = accountRepository.save(acc);
            for (User usr : usersIn) {
                var c = usr.getAccounts();
                c.add(account);
                usr.setAccounts(c);
                usersService.Update(usr);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
