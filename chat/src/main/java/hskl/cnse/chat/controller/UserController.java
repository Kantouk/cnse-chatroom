package hskl.cnse.chat.controller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import hskl.cnse.chat.db.dto.UserRegistrationDto;
import hskl.cnse.chat.db.model.Role;
import hskl.cnse.chat.db.model.User;
import hskl.cnse.chat.db.repositories.RoleRepository;
import hskl.cnse.chat.db.repositories.UserRepository;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto, BindingResult result) {

        // Überprüfe die Benutzereingabe
        if (result.hasErrors()) {
            return "registration"; // Formular erneut anzeigen mit Fehlermeldungen
        }

        /* 
         // Prüfe ob die E-Mail ein @ Zeichen besitzt
         if (!userRegistrationDto.getEmail().contains("@")) {
            return "registration";
        }

        // Prüfe ob die E-Mail bereits vergeben ist
        if (userRepository.findByEmail(userRegistrationDto.getEmail()) != null) {
            return "registration";
        }

        // Validiere das Passwort auf Mindestlänge
        if (userRegistrationDto.getPassword().length() < 1) {
            return "registration";
        }

        */

        // Erstelle ein neues Benutzerobjekt
        User user = new User();
        user.setEmail(userRegistrationDto.getEmail());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        // Setze die Standardrolle für den Benutzer (z.B., "USER")
        String roleName = userRegistrationDto.getRole() != null ? userRegistrationDto.getRole() : "USER";
        Role userRole = new Role(roleName);
        user.setRoles(Collections.singletonList(userRole));


        // Speichere den Benutzer in der Datenbank
        userRepository.save(user);
        roleRepository.save(userRole); //

        // Logge Informationen über die Registrierung
        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");
        logger.info("Benutzer " + user.getEmail() + " wurde erfolgreich registriert");
        logger.info("Benutzer " + user.getEmail() + " hat folgende Rollen: " + user.getRoles());
        logger.info("Benutzer " + user.getEmail() + " hat folgendes Passwort: " + user.getPassword());
        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");
        logger.info("*********************************************************************************");

        // Weiterleitung zur Chat-Seite oder einer Bestätigungsseite
        return "chat";
    }
}
