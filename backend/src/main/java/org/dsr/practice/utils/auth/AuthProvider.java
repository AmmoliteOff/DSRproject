package org.dsr.practice.utils.auth;

import org.dsr.practice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthProvider implements AuthenticationProvider {
    AuthService authService;

    public AuthProvider(@Autowired AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        try{
            String name = authentication.getName();
            String password = authentication.getCredentials().toString();
            ArrayList<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
            String role = authService.GetRole(name,password);
            if(role!=null) {
                authService.changeCode(name);
                grantedAuthority.add(new SimpleGrantedAuthority(role));
                return new UsernamePasswordAuthenticationToken(
                        name, password, grantedAuthority);
            }
            else{
                return null;
            }
        }
        catch (AuthenticationException e){
            return null;
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}