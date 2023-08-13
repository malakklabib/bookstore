package com.example.bookstore.domain;

import com.example.bookstore.domain.validator.PasswordsMatch;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Document
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
@PasswordsMatch
public class Users implements UserDetails {

    @Id
    private String id;

    @NonNull
    @Size(min = 4, max = 50, message = "Please enter your full name")
    @NotEmpty(message = "Please enter your full name")
    private String name;

    @NonNull
    @Size(min = 8, max = 20, message = "Please enter a password between 8 and 20 characters")
    @NotEmpty(message = "Please enter a password")
    private String password;

    @Transient
    @NotEmpty(message = "Please confirm your password")
    private String confirmPassword;

    @NonNull
    @Size(min = 10, max = 25, message = "Please enter a valid email")
    @NotEmpty(message = "Please enter your email")
    private String email;

    @NonNull
    private ShoppingCart shoppingCart;

    @NonNull
    private Wishlist wishlist;

    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
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
