package com.example.gdzunit.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

// Это энтити с полями id, username, password, variant(id), roles
// Он реализует интерфейс UserDetails, что позволяет работать со Spring Security
@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @JoinColumn(name = "variant_id")
    private Variant variant;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private Set<Role> roles;

    @Column(name = "enabled")
    private Boolean enabled;

    // TODO: сделать аватарки пользователям
    @Column(name = "avatar")
    private String avatarURL;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(String username, String password, Variant variant, Set<Role> roles, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.variant = variant;
        this.roles = roles;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
