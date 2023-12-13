package hskl.cnse.chat.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService, UserDetailsPasswordService {
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        // set password for specified user in db
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // return specified user from db
    }
}
