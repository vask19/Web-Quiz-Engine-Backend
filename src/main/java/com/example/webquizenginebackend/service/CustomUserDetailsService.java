package com.example.webquizenginebackend.service;


import com.example.webquizenginebackend.entity.Users;
import com.example.webquizenginebackend.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

        return build(user);
    }

    private UserDetails build(Users user) {
        List<GrantedAuthority> grantedAuthorities =
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new Users(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities
        );
    }
}
