package ToDo.app;

import ToDo.app.domain.Users;
import ToDo.app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ToDoApplication.class, args);
	}

}
