package com.mode.init;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders
        .AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration
        .WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mode.security.AuthenticationTokenProcessingFilter;
import com.mode.security.CsrfSecurityRequestMatcher;
import com.mode.security.UnauthorizedEntryPoint;
import com.mode.security.UserDetailsServiceImpl;

/**
 * This is the java annotations based configurations for Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Bean(name = "userDetailsService")
    protected UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean(name = "unauthorizedEntryPoint")
    protected UnauthorizedEntryPoint unauthorizedEntryPoint() {
        return new UnauthorizedEntryPoint();
    }

    @Bean(name = "csrfSecurityRequestMatcher")
    protected CsrfSecurityRequestMatcher csrfSecurityRequestMatcher() {
        return new CsrfSecurityRequestMatcher();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new AuthenticationTokenProcessingFilter(userDetailsService()),
                        UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests().antMatchers("/login", "/signup", "/error**", "/me")
                .permitAll()
                .anyRequest().hasAuthority("USER");

        http.exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint())
                .accessDeniedPage("/error/403");

        csrfSecurityRequestMatcher().setExecludeUrls(excludedUrls());
        http.csrf().requireCsrfProtectionMatcher(csrfSecurityRequestMatcher());
    }

    /*
     * Add the excluded urls for csrf
     */
    private List<String> excludedUrls() {
        List<String> execludeUrls = new ArrayList<String>();

        execludeUrls.add("/");

        return execludeUrls;
    }
}