package com.mode.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mode.base.BaseConfig;
import com.mode.base.Response;
import com.mode.dao.ProfileDAO;
import com.mode.domain.Profile;
import com.mode.security.AuthenticatedUser;

/**
 * A profile API that returns the profile of current logged in user.
 */
@RestController
public class ProfileAPI {
    @Autowired
    private ProfileDAO profileDAO;

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public Response getProfile(@AuthenticationPrincipal AuthenticatedUser user) {
        Response res = new Response();
        if (user == null) {
            res.setCode(BaseConfig.OPERATION_FAILED);
            res.setMessage("User Not Logged In.");
        } else {
            // Successful
            res.setCode(BaseConfig.OPERATION_SUCCEEDED);
            res.setMessage(BaseConfig.SUCCESSFUL_MESSAGE);
            res.setPayload(profileDAO.getProfile(user.getUserId()));
        }
        return res;
    }
}