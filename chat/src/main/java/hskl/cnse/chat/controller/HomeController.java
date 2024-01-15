package hskl.cnse.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Deine Logik zum Bearbeiten des "/"-Endpunkts
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        // Deine Logik zum Bearbeiten des "/index.html"-Endpunkts
        return "index"; // Hier wird angenommen, dass "index" der Name deiner HTML-Vorlage oder Ansicht ist
    }

    @GetMapping("/chat")
    public String chat() {
        // Deine Logik zum Bearbeiten des "/chat.html"-Endpunkts
        return "chat"; // Hier wird angenommen, dass "chat" der Name deiner HTML-Vorlage oder Ansicht ist
    }
}
