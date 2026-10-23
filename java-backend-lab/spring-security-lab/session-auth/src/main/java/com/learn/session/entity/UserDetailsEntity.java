package com.learn.session.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.learn.session.model.Role;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;

@Entity 
public class UserDetailsEntity implements UserDetails{

    @Id 
    private UUID id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    protected UserDetailsEntity() {
        // Required by JPA
    }

    public UserDetailsEntity(UUID id, String username, String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = ( roles == null || roles.isEmpty()) ? Set.of(Role.USER) : roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
