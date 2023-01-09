package ToDo.app;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.domain.Priority;
import ToDo.app.domain.Role;
import ToDo.app.domain.Task;
import ToDo.app.domain.Users;
import ToDo.app.service.DirectoryService;
import ToDo.app.service.EventService;
import ToDo.app.service.TaskService;
import ToDo.app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(ToDoApplication.class, args);
	}
    
}
