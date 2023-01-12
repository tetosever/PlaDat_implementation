package ToDo.app.controller;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Priority;
import ToDo.app.domain.Role;
import ToDo.app.domain.Task;
import ToDo.app.domain.Users;
import ToDo.app.repository.DirectoryRepository;
import ToDo.app.repository.TaskRepository;
import ToDo.app.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DirectoryRepository directoryRepository;

    @BeforeEach
    public void setup() {
        taskRepository.deleteAll();
    }
    
    @Test
    public void getAllTaskWithViewTest() throws Exception {
        Task task = createTask("Prova", "prova prova", Priority.High, 
                createUsers("Matteo", "Severgnini", Role.BusinessManager),
                createDirectory("Università", createDirectory("Esame1", null)));
        
        assertTrue(taskRepository.findAll().size() == 1);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("home.html"));
    }

    @Test
    public void deleteTaskByIdWithViewTest() throws Exception {
        Task task = createTask("Prova", "prova prova", Priority.High,
                createUsers("Matteo", "Severgnini", Role.BusinessManager),
                createDirectory("Università", createDirectory("Esame1", null)));
        
        assertTrue(taskRepository.findAll().size() == 1);

        mockMvc.perform(get("/tasks/delete/" + task.getId().toString()))
                .andExpect(redirectedUrl("/"));

        assertTrue(taskRepository.findAll().size() == 0);
        
    }
    
    @Test
    public void addTaskWithViewTest() throws Exception {
        createTask("Prova", "prova prova", Priority.High,
                createUsers("Matteo", "Severgnini", Role.BusinessManager),
                createDirectory("Università", createDirectory("Esame1", null)));

        assertTrue(taskRepository.findAll().size() == 1);

        mockMvc.perform(post("/tasks/create")
                        .param("description", "prova 2")
                        .param("title", "Prova2")
                        .param("priority", "High")
                        .param("user_id", createUsers("Mario", "Rossi", Role.BusinessManager).getId().toString())
                        .param("directory_id", createDirectory("Lavoro", null).getId().toString()))
                .andExpect(redirectedUrl("/"));

        assertTrue(taskRepository.findAll().size() == 2);

    }
    
    @Test
    public void updateTaskWithViewTest() throws Exception {
        Task task = createTask("Prova", "prova prova", Priority.High,
                createUsers("Matteo", "Severgnini", Role.BusinessManager),
                createDirectory("Università", createDirectory("Esame1", null)));

        assertTrue(taskRepository.findAll().size() == 1);

        mockMvc.perform(post("/tasks/update/" + task.getId().toString())
                        .param("description", "prova 2")
                        .param("title", "Prova2")
                        .param("priority", "High")
                        .param("user_id", createUsers("Mario", "Rossi", Role.BusinessManager).getId().toString())
                        .param("directory_id", createDirectory("Lavoro", null).getId().toString()))
                .andExpect(redirectedUrl("/"));

        assertTrue(taskRepository.findAll().size() == 1);
        assertEquals("Title should be equals", taskRepository.findAll().get(0).getTitle(), "Prova2");

    }
    
    private Task createTask(String title, String description, Priority priority, Users users, Directory directory) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(priority);
        List<Users> userList = new ArrayList<>();
        userList.add(users);
        task.setUsersList(userList);
        task.setDirectory(directory);
        return taskRepository.save(task);
    }

    private Users createUsers(String name, String surname, Role role){
        Users users = new Users();
        users.setName(name);
        users.setSurname(surname);
        users.setRole(role);
        return userRepository.save(users);
    }
    
    private Directory createDirectory(String name, Directory directory) {
        Directory directory_tmp = new Directory();
        directory_tmp.setName(name);
        directory_tmp.setDirectory(directory);
        return directoryRepository.save(directory_tmp);
    }
    
    
    
}
