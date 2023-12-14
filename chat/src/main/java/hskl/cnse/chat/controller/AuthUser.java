package hskl.cnse.chat.controller;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

class AuthUser implements UserDetails {

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> roles;

    public AuthUser(String email, String password, Collection<? extends GrantedAuthority> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    // Getters for email, password, and roles

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", getRoles()));
    }

    CharSequence getRoles() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    // Implement other methods like isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled
    // These methods return boolean values indicating whether the user account is valid.

    // Example:
    @Override
    public boolean isAccountNonExpired() {
        return true; // You may implement the logic based on your requirements.
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

    public void setUserPassword(String newPassword) {
        this.password = newPassword;
    }
}
