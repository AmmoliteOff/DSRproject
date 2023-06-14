package org.dsr.practice.utils.auth;

//public class AccountingUserDetailsService {
//}

import org.dsr.practice.dao.UsersDAO;
import org.dsr.practice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AccountingUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersDAO usersDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersDao.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new AccountingUserPrincipal(user);
    }
}