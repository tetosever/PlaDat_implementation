package ToDo.app.service;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.domain.Priority;
import ToDo.app.domain.Role;
import ToDo.app.domain.Task;
import ToDo.app.domain.Users;
import ToDo.app.repository.DirectoryRepository;
import ToDo.app.repository.TaskRepository;
import ToDo.app.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TaskServiceIntegrationTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DirectoryRepository directoryRepository;

    private Directory directory;
    private Users users1;
    private Users users2;
    private Task task1;
    private Task task2;
    private Task task3;
    private List<Task> taskList = new ArrayList<>();
    private List<Users> usersList = new ArrayList<>();

    @Before
    public void setup() {
        //directory risulterebbe la cartella padre
        directory = createDirectory("Università", null);
        directory = directoryRepository.save(directory);

        users1 = createUsers("Matteo", "Severgnini", Role.BusinessManager);
        users1 = userRepository.save(users1);
        users2 = createUsers("Mattia", "Piazzalunga", Role.BusinessManager);
        users2 = userRepository.save(users2);

        task1 = createTask("Prova1", "Prova 1 descrizione", Priority.High,
            users1, createDirectory("Esame1", directory));
        task1 = taskRepository.save(task1);

        task2 = createTask("Prova1", "Prova 1 descrizione", Priority.High,
            users1, createDirectory("Esame2", directory));
        task2 = taskRepository.save(task2);

        task3 = createTask("Prova1", "Prova 1 descrizione", Priority.High,
            users2, createDirectory("Esame3", directory));
        task3 = taskRepository.save(task3);

        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        usersList.add(users1);
        usersList.add(users2);
    }

    @After
    public void reset() {
        taskRepository.deleteAll();
        directoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getAllByFilter_whenOnlyTitleTest() {
        List<Task> taskList = taskService.getAllByFilter(null, "Prova1", null, null);
        assertTrue(taskList.size() == 3);
        assertEquals("Event1 title should be equals", taskList.get(0).getTitle(), "Prova1");
        assertEquals("Event2 title should be equals", taskList.get(1).getTitle(), "Prova1");
        assertEquals("Event3 title should be equals", taskList.get(2).getTitle(), "Prova1");

        assertEquals("Directory name should be equals", taskList.get(0).getDirectory().getName(), "Esame1");
        assertEquals("Directory name should be equals", taskList.get(1).getDirectory().getName(), "Esame2");
        assertEquals("Directory name should be equals", taskList.get(2).getDirectory().getName(), "Esame3");
    }

    @Test
    public void getAllByFilter_whenOnlyStartDateTest() {
        List<Task> taskList = taskService.getAllByFilter(null, null, Priority.High.toString(), null);
        assertTrue(taskList.size() == 3);
        assertEquals("Event1 title should be equals", taskList.get(0).getTitle(), "Prova1");

        assertEquals("Directory name should be equals", taskList.get(0).getDirectory().getName(), "Esame1");
    }

    @Test
    public void getAllByFilter_whenOnlyUserTest() {
        List<Task> taskList = taskService.getAllByFilter(null, null, null, "Severgnini");
        assertTrue(taskList.size() == 2);
        assertEquals("Event1 title should be equals", taskList.get(0).getTitle(), "Prova1");
        assertEquals("Event2 title should be equals", taskList.get(1).getTitle(), "Prova1");

        assertEquals("Directory name should be equals", taskList.get(0).getDirectory().getName(), "Esame1");
        assertEquals("Directory name should be equals", taskList.get(1).getDirectory().getName(), "Esame2");

    }

    @Test
    public void getAllByFilter_whenAllParametersDoesNotFoundTest() {
        List<Task> taskList = taskService.getAllByFilter(null, "Prova1", Priority.High.toString(), "Piazza");
        assertTrue(taskList.size() == 0);
    }

    @Test
    public void getAllByFilter_whenAllParametersFoundTest() {
        List<Task> taskList = taskService.getAllByFilter(null, "Prova1", Priority.High.toString(), "Severgnini");
        assertTrue(taskList.size() == 2);
        assertEquals("Event1 title should be equals", taskList.get(0).getTitle(), "Prova1");
        assertEquals("Event1 title should be equals", taskList.get(1).getTitle(), "Prova1");
        
        assertEquals("Directory name should be equals", taskList.get(0).getDirectory().getName(), "Esame1");
        assertEquals("Directory name should be equals", taskList.get(1).getDirectory().getName(), "Esame2");

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
