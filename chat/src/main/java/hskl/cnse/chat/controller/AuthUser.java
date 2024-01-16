package hskl.cnse.chat.controller;
/* 
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

class AuthUser implements UserDetails {

    private Collection<String> roles;
    private String email;
    private String password;
    private boolean accountNonExpired;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", getRoles()));
    }

    private Collection<String> getRoles() {
        return this.roles;
    }

    @Override
    public String getUsername() {

        return this.getEmail();
    }

    private String getEmail() {

        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {

        // return this.accountNonExpired;
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // return this.accountNonExpired;
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

}*/