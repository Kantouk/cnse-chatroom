package hskl.cnse.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class ChatApplication {

  @GetMapping("/index")
  public String welcome() {
       return "index";
    }
  
    @GetMapping("/user")
    public Principal user(Principal principal) {
        System.out.println("username : " + principal.getName());
        return principal;
    }

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}
 
}

