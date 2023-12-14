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
        //TODO: update password in db
        throw new UnsupportedOperationException("Unimplemented method 'updatePassword'");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: return specified user from db
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

}
