package com.mode.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mode.dao.AccountDAO;
import com.mode.domain.Account;

/**
 * Implements Spring Security {@link UserDetailsService} that is injected into
 * authentication provider in configuration.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountDAO accountDAO;

    public UserDetails loadUserByUsername(final String username) {

        String login = username.toLowerCase();

        Account account = accountDAO.getAccount(username, null);

        if (account == null) {
            throw new UsernameNotFoundException("User " + login + " was not found in the database");
        } else if (!account.isActivated()) {
            throw new UserNotActivatedException("User " + login + " is not activated");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (account.getRoles() != null) {
            String[] roles = account.getRoles().split(",");
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }

        return new AuthenticatedUser(account.getId(), username, account.getPassword(), authorities);
    }
}