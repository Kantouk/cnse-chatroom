package hskl.cnse.chat.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hskl.cnse.chat.bean.AuthUser;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Logic for handling the home page
        return "index";
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal(expression = "authUser") AuthUser authUser, Model model) {
        handleSecuredRequest(authUser, model);
        return "chat";
    }

    @GetMapping("/authenticated")
    public String handleGet(@AuthenticationPrincipal(expression = "authUser") AuthUser authUser, Model model) {
        if (authUser.getRoles().contains("ADMIN")) {
            // Logic for handling the request for ADMIN
            return "chat";
        } else {
            // Logic for handling the request for other users
            handleSecuredRequest(authUser, model);
            return "chat";
        }
    }

    private void handleSecuredRequest(AuthUser authUser, Model model) {
        // Common logic for handling secured requests
        model.addAttribute("user", authUser);
    }
}
