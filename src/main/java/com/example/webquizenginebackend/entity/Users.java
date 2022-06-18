package com.example.webquizenginebackend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @OneToMany(cascade =  CascadeType.ALL,fetch =  FetchType.LAZY,mappedBy = "user",orphanRemoval = true)
    private List<Quiz> quizList = new ArrayList<>();

    @Column
    @ElementCollection
    private List<Integer> completed;

    @Column
    private boolean isActive = true;
    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @Column
    private String role = "ROLE_USER";


    public Users(Long id, String username, String password, List<GrantedAuthority> grantedAuthorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword(){
        return password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }
    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }
    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
