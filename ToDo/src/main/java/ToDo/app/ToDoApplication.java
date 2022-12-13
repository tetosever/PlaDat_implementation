package ToDo.app;

import ToDo.app.domain.Users;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoApplication.class, args);
		Users users = new Users();
		users.setName("Matteo");
		users.setSurname("Severgnini");
	}

}
