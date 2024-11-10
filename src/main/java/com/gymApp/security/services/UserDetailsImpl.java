package com.gymApp.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gymApp.model.User;

@NoArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String username;
    private String email;

    @JsonIgnore
    private String password;

    private GrantedAuthority authority;

    public UserDetailsImpl(Long userId, String username, String email, String password, GrantedAuthority authority) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public static UserDetailsImpl build(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUserId(user.getUserid());
        userDetails.setUsername(user.getUserName());
        userDetails.setEmail(user.getEmail());
        userDetails.setPassword(user.getPassword());
        userDetails.setAuthority(authority);

        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(authority);  // Επιστρέφω single-element list γιατί έχει μια μόνο authority το member
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(userId, user.userId);
    }

}