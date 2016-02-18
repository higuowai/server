package com.mode.service;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mode.base.BaseConfig;
import com.mode.base.ServiceException;
import com.mode.dao.AccountDAO;
import com.mode.dao.ProfileDAO;
import com.mode.domain.Account;
import com.mode.domain.Profile;
import com.mode.util.TokenHandler;

@Service
public class AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ProfileDAO profileDAO;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authManager;

    /**
     * Sign up: add a new record to both Account and Profile repositories.
     *
     * @param login
     * @param password
     * @param profile
     * @return access token
     */
    public String createAccount(String login, String password, String role, Profile profile)
            throws ServiceException {

        /* Check if this account already exists. */
        Account userAccount = accountDAO.getAccount(login, null);
        if (userAccount != null) {
            throw new ServiceException("Account already exists.");
        }

        // Get the current time
        final Long currentTimeMillis = System.currentTimeMillis();

        // Wrap up an account object
        Account account = new Account();
        account.setPassword(password);
        account.setLogin(login);
        account.setRoles(role);
        account.setActivated(true);
        account.setCtime(currentTimeMillis);
        account.setUtime(currentTimeMillis);

        // Wrap up a profile object
        profile.setCtime(currentTimeMillis);
        profile.setUtime(currentTimeMillis);

        // create the account and profile
        Account ret = createAccountInternal(account, profile);
        if (ret == null) {
            throw new ServiceException("An internal error happened while creating your " +
                    "account.");
        }

        return ret.getAccessToken();
    }

    /**
     * Create profile and account for the user
     *
     * @param account
     * @param profile
     * @return
     */
    private Account createAccountInternal(Account account, Profile profile) {

        /* Add a new account to database. */
        accountDAO.createAccount(account);

        /* Create an access token and save to database. */
        refreshAccessToken(account.getLogin());

        /* Query and double check if account has been created. */
        Account createdAcct = accountDAO.getAccount(account.getLogin(), null);
        if ((createdAcct == null) || !createdAcct.getLogin().equals(account.getLogin())) {
            return null;
        }

        /* We'll need to add a new profile for this new account. */
        profile.setUserId(createdAcct.getId());
        profileDAO.createProfile(profile);

        return createdAcct;
    }

    /**
     * Return an access token string after successfully login.
     */
    private String refreshAccessToken(String username) {

        String token = TokenHandler.createTokenForUser(username);

        Account account = new Account();
        account.setLogin(username);
        account.setAccessToken(token);
        account.setUtime(System.currentTimeMillis());
        accountDAO.updateAccount(account);

        return token;
    }

    /**
     * Everytime a username and password authentication will return a new access token.
     * @param login
     * @param password
     * @return
     */
    public String login(String login, String password) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login, password);
        // try to validate username and password
        Authentication authentication = authManager.authenticate(authenticationToken);
        // Save the successfully authenticated object in security context.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Everytime a username and password authentication will make a new access token.
        return refreshAccessToken(login);
    }
}