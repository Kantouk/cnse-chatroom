package hskl.cnse.chat.controller;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Logic for handling the home page
        return "index";
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal AuthUser authUser, Model model) {
        if (authUser.getRoles().contains("ADMIN")) {
            // Logic for handling secured page for ADMIN
            return "adminPage";
        } else {
            // Logic for handling secured page for other users
            model.addAttribute("user", authUser);
            return "userPage";
        }
    }

    @GetMapping("/handleGet")
    public String handleGet(@AuthenticationPrincipal AuthUser authUser, Model model) {
        if (authUser.getRoles().contains("ADMIN")) {
            // Logic for handling the request for ADMIN
            return "adminResponse";
        } else {
            // Logic for handling the request for other users
            model.addAttribute("user", authUser);
            return "userResponse";
        }
    }
}

