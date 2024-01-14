package hskl.cnse.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@EntityScan("hskl.cnse.chat.db.model")
public class ChatApplication {

  @GetMapping("/index")
  public String welcome() {
       return "index";
    }

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}
 
}

