package com.example.webquizenginebackend.security;

import com.example.webquizenginebackend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, RestAuthenticationEntryPoint authenticationEntryPoint) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .csrf().disable().headers().frameOptions().disable()
                .and()// for Postman, the H2 console
                .authorizeRequests() //
                .antMatchers("/api/quizzes/**").hasAnyRole("USER")
// manage access
                .antMatchers( "/api/register").permitAll()
                .antMatchers("/actuator/shutdown").permitAll() // needs to run test
                // other matchers
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(getEncoder());
    }


    @Bean
    public BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }
}
