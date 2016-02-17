package com.mode.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.mode.base.BaseConfig;
import com.mode.util.TokenHandler;

import io.jsonwebtoken.ExpiredJwtException;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    private final UserDetailsService userService;

    public AuthenticationTokenProcessingFilter(UserDetailsService userService) {
        this.userService = userService;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = getAsHttpRequest(request);
        HttpServletResponse httpResponse = getAsHttpResponse(response);

        String accessToken = extractAccessTokenFromRequest(httpRequest);

        if (accessToken != null) {
            /* Decode user information from token */
            try {
                String username = TokenHandler.parseUserFromToken(accessToken);
                UserDetails userDetails = userService.loadUserByUsername(username);

                /* Check if the account is locked */
                if (userDetails.isAccountNonLocked()) {
                     /* Check if the username and password is valid */
                    UsernamePasswordAuthenticationToken authentication = new
                            UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
                            "Access forbidden, please activate your account.");
                    return;
                }
            } catch (ExpiredJwtException e) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Token expired, please login.");
                    return;
            } catch (Exception e) {
                    /* If token is not presented, return unauthorized error */
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            "Access denied, please login.");
                    return;
            }
        }
        chain.doFilter(request, response);
    }

    private HttpServletRequest getAsHttpRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }

        return (HttpServletRequest) request;
    }

    private HttpServletResponse getAsHttpResponse(ServletResponse response) {
        if (!(response instanceof HttpServletResponse)) {
            throw new RuntimeException("Expecting an Http response");
        }
        return (HttpServletResponse) response;
    }

    private String extractAccessTokenFromRequest(HttpServletRequest httpRequest) {
        /* Get token from header */
        String accessToken = httpRequest.getHeader("X-Access-Token");

        /* If token not found get it from request parameter */
        if (accessToken == null) {
            accessToken = httpRequest.getParameter("token");
        }

        return accessToken;
    }

}