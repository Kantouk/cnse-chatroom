package hskl.cnse.chat.services;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import hskl.cnse.chat.db.dto.UserRegistrationDto;
import hskl.cnse.chat.db.model.AuthUser;
import hskl.cnse.chat.db.model.Role;
import hskl.cnse.chat.db.repositories.RoleRepository;
import hskl.cnse.chat.db.repositories.UserRepository;

@Service
public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    

    public AuthUser saveUser(UserRegistrationDto userDto) {
        AuthUser user = new AuthUser();

        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));

        return userRepository.save(user);
    }

    private Role checkRoleExist() {
        Role role = roleRepository.findByName("USER");
        if (role == null) {
            role = new Role();
            role.setName("USER");
            roleRepository.save(role);
        }
        return role;
    }

    public AuthUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserRegistrationDto> findAllUsers() {
        List<AuthUser> users = userRepository.findAll();
        return users.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private UserRegistrationDto convertEntityToDto(AuthUser user) {
        UserRegistrationDto userDto = new UserRegistrationDto();
        String[] nameParts = user.getName().split(" ", 2);
        userDto.setFirstName(nameParts[0]);
        if (nameParts.length > 1) {
            userDto.setLastName(nameParts[1]);
        }

        return userDto;
    }

    public AuthUser saveOAuth2UserFromAuthentication(OAuth2AuthenticationToken authentication) {

        UserRegistrationDto userDto = new UserRegistrationDto();

        String email = (String) authentication.getPrincipal().getAttributes().get("email");
        logger.info("#################################################################");
        logger.info("email: " + email);
        logger.info("#################################################################");
        userDto.setEmail(email);

        String nameAttribute = (String) authentication.getPrincipal().getAttributes().get("name");
        if (nameAttribute != null) {
            String[] name = nameAttribute.split(" ");
            userDto.setFirstName(name[0]);
            if (name.length > 1) {
                userDto.setLastName(name[1]);
            }
        } else {
            // Setzen Sie hier einen Standardnamen
            userDto.setFirstName("Default");
            userDto.setLastName("User");
        }
        logger.info("#################################################################");
        logger.info("name: " + nameAttribute);
        logger.info("#################################################################");

        userDto.setPassword("GOOGLE"); // Setzen Sie hier ein Standardpasswort oder ein generiertes Passwort

        Role userRole = roleRepository.findByName("USER"); // Setzen Sie die Rolle auf "USER"
        checkRoleExist();
        userDto.setRoles(Collections.singletonList(userRole));

        return saveUser(userDto);
    }

}
