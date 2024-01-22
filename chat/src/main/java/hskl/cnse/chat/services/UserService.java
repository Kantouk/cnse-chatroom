package hskl.cnse.chat.services;


import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hskl.cnse.chat.db.dto.UserRegistrationDto;
import hskl.cnse.chat.db.repositories.*;
import hskl.cnse.chat.db.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ChatRepository chatRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ChatRepository chatRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.chatRepository = chatRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserRegistrationDto userDto) {
        AuthUser user = new AuthUser();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        //encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    public AuthUser findUserByEmail(String email) {
        AuthUser authUser = userRepository.findByEmail(email);
        return authUser;
    }

    public List<UserRegistrationDto> findAllUsers() {
        List<AuthUser> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserRegistrationDto convertEntityToDto(AuthUser user) {
        UserRegistrationDto userDto = new UserRegistrationDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public void updateChatsForUser(@NonNull Long userId) {
        AuthUser user = userRepository.findById(userId).orElse(null);
        List<Chat> chats = chatRepository.findByUser_Id(userId);
        user.getChats().addAll(chats);
        userRepository.save(user);
    }
}
