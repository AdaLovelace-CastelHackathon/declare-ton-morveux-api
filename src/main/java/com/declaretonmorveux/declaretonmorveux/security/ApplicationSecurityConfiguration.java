package com.declaretonmorveux.declaretonmorveux.security;

import com.declaretonmorveux.declaretonmorveux.security.jwt.JwtAuthenticationEntryPoint;
import com.declaretonmorveux.declaretonmorveux.security.jwt.JwtRequestFilter;
import com.declaretonmorveux.declaretonmorveux.service.ParentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ParentService parentService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder, ParentService parentService) {
        this.passwordEncoder = passwordEncoder;
        this.parentService = parentService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
        .antMatchers(HttpMethod.POST, "/register").permitAll()
        .antMatchers(HttpMethod.GET, "/isAuthenticated").permitAll()
        .antMatchers(HttpMethod.GET, "/getUser").authenticated()
        .antMatchers(HttpMethod.GET, "/api/schools/**").permitAll()
        .antMatchers(HttpMethod.POST, "/api/schools/**").permitAll()
        .antMatchers(HttpMethod.GET, "/api/children/**").permitAll()
        .antMatchers(HttpMethod.POST, "/api/children/**").permitAll()
        .antMatchers("/**").denyAll()
        .and()
        .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .logout().deleteCookies("sessionId");

        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(parentService);

        return provider;
    }
}
