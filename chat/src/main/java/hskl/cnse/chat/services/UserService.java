package hskl.cnse.chat.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import hskl.cnse.chat.db.model.User;
import hskl.cnse.chat.web.dto.UserRegistrationDto;


public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
