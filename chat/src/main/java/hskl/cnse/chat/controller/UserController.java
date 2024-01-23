package hskl.cnse.chat.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import hskl.cnse.chat.db.dto.UserRegistrationDto;
import hskl.cnse.chat.db.model.AuthUser;
import hskl.cnse.chat.services.*;

import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserRegistrationDto user = new UserRegistrationDto();
        model.addAttribute("user", user);
        return "registration";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserRegistrationDto userDto,
                               BindingResult result,
                               Model model) {
        AuthUser existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", "Email already exists",
                    "There is already an account registered with the same email");
        }

        
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            System.out.println("#################################################################################");
            System.out.println("Error: " + result.getAllErrors().toString());
            System.out.println("#################################################################################");
        } else {
            userService.saveUser(userDto);
            AuthUser savedUser = userService.findUserByEmail(userDto.getEmail());

            
            System.out.println("#################################################################################");
            System.out.println("Roles for user " + savedUser.getEmail() + ": " + savedUser.getRoles());
            System.out.println("#################################################################################");

            return "redirect:/?success";
        }

        return "redirect:/register?error";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model) {
        List<UserRegistrationDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/user/id")
    public ResponseEntity<Long> getUserByName() {
        // Zugriff auf die Authentication-Instanz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Überprüfe, ob der Benutzer authentifiziert ist
        if (authentication != null && authentication.isAuthenticated()) {
            // Hier kannst du auf verschiedene Informationen zugreifen, z.B.:
            String username = authentication.getName();

            AuthUser authUser = userService.findUserByEmail(username);
            Long id = authUser.getId();

            System.out.println(username + id);

            return ResponseEntity.ok(id);
    } else {
        return ResponseEntity.status(0).build();
    }
    }

    @GetMapping("/user/name")
    public ResponseEntity<String> getUserName() {
        // Zugriff auf die Authentication-Instanz
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Überprüfe, ob der Benutzer authentifiziert ist
        if (authentication != null && authentication.isAuthenticated()) {
            // Hier kannst du auf verschiedene Informationen zugreifen, z.B.:
            String username = authentication.getName();
            return ResponseEntity.ok(username);
    } else {
        return ResponseEntity.status(0).build();
    }
    }
    
}