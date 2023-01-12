package ToDo.app.controller;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.domain.Priority;
import ToDo.app.domain.Role;
import ToDo.app.domain.Task;
import ToDo.app.domain.Users;
import ToDo.app.repository.DirectoryRepository;
import ToDo.app.repository.EventRepository;
import ToDo.app.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DirectoryRepository directoryRepository;

    @BeforeEach
    public void setup() {
        eventRepository.deleteAll();
    }
    
    @Test
    public void getAllTaskWithViewTest() throws Exception {
        Event event = createEvent("Prova", LocalDate.now(), LocalDate.now(), "Bergamo",
                createUsers("Matteo", "Severgnini", Role.BusinessManager),
                createDirectory("Università", createDirectory("Esame1", null)));
        
        assertTrue(eventRepository.findAll().size() == 1);

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("home.html"));
    }

    @Test
    public void deleteTaskByIdWithViewTest() throws Exception {
        Event event = createEvent("Prova", LocalDate.now(), LocalDate.now(), "Bergamo",
                createUsers("Matteo", "Severgnini", Role.BusinessManager),
                createDirectory("Università", createDirectory("Esame1", null)));

        assertTrue(eventRepository.findAll().size() == 1);

        mockMvc.perform(get("/events/delete/" + event.getId().toString()))
                .andExpect(redirectedUrl("/"));

        assertTrue(eventRepository.findAll().size() == 0);
        
    }
    
    @Test
    public void addTaskWithViewTest() throws Exception {
        Event event = createEvent("Prova", LocalDate.now(), LocalDate.now(), "Bergamo",
                createUsers("Matteo", "Severgnini", Role.BusinessManager),
                createDirectory("Università", createDirectory("Esame1", null)));

        assertTrue(eventRepository.findAll().size() == 1);

        mockMvc.perform(post("/events/create")
                        .param("title", "Prova2")
                        .param("start_date", event.getStart_date().toString())
                        .param("place", "Milano")
                        .param("user_id", createUsers("Mario", "Rossi", Role.BusinessManager).getId().toString())
                        .param("directory_id", createDirectory("Lavoro", null).getId().toString()))
                .andExpect(redirectedUrl("/"));

        assertTrue(eventRepository.findAll().size() == 2);

    }
    
    @Test
    public void updateTaskWithViewTest() throws Exception {
        Event event = createEvent("Prova",LocalDate.now(), LocalDate.now(), "Bergamo",
                createUsers("Matteo", "Severgnini", Role.BusinessManager),
                createDirectory("Università", createDirectory("Esame1", null)));

        assertTrue(eventRepository.findAll().size() == 1);

        mockMvc.perform(post("/events/update/" + event.getId().toString())
                        .param("title", "Prova2")
                        .param("start_date", event.getStart_date().toString())
                        .param("place", "Milano")
                        .param("user_id", createUsers("Mario", "Rossi", Role.BusinessManager).getId().toString())
                        .param("directory_id", createDirectory("Lavoro", null).getId().toString()))
                .andExpect(redirectedUrl("/"));

        assertTrue(eventRepository.findAll().size() == 1);
        assertEquals("Title should be equals", eventRepository.findAll().get(0).getTitle(), "Prova2");

    }
    
    private Event createEvent(String title, LocalDate start_date, LocalDate end_date, String place, Users users, Directory directory) {
        Event event = new Event();
        event.setTitle(title);
        event.setStart_date(start_date);
        event.setEnd_date(end_date);
        event.setPlace(place);
        List<Users> userList = new ArrayList<>();
        userList.add(users);
        event.setUsersList(userList);
        event.setDirectory(directory);
        return eventRepository.save(event);
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
