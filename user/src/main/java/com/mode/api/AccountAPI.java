package com.mode.api;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mode.base.BaseConfig;
import com.mode.base.Response;
import com.mode.base.ServiceException;
import com.mode.domain.Account;
import com.mode.domain.Profile;
import com.mode.service.AccountService;

/**
 * This is the controller for serving account restful api;
 *
 * @author chao
 */
@RestController
public class AccountAPI {

    @Autowired
    private AccountService accountService;

    /**
     * Sign up via login and password. Here login could be either email or mobile.
     *
     * @param login
     * @param password
     * @param profile
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Response signup(@RequestHeader("login") String login,
                           @RequestHeader("password") String password,
                           @RequestBody Profile profile) {
        Response res = new Response();
        try {
            final String accessToken = accountService.createAccount(login, password, BaseConfig
                            .ROLE_USER, profile);
            res.setCode(BaseConfig.OPERATION_SUCCEEDED);
            res.setMessage(BaseConfig.SUCCESSFUL_MESSAGE);
            res.setPayload(accessToken);
        } catch (ServiceException e) {
            res.setCode(BaseConfig.OPERATION_FAILED);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    /**
     * Login via email/mobile and password.
     *
     * @param login
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestHeader("login") String login,
                          @RequestHeader("password") String password) {

        Response res = new Response();
        try {
            final String accessToken = accountService.login(login, password);
            res.setCode(BaseConfig.OPERATION_SUCCEEDED);
            res.setMessage(BaseConfig.SUCCESSFUL_MESSAGE);
            res.setPayload(accessToken);
        } catch (AuthenticationException e) {
            res.setCode(BaseConfig.OPERATION_FAILED);
            res.setMessage(e.getMessage());
        }
        return res;
    }
}