package org.dsr.practice.utils.auth;

//public class AccountingUserDetailsService {
//}

import org.dsr.practice.models.User;
import org.dsr.practice.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AccountingUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersService.getUser(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new AccountingUserPrincipal(user);
    }
}