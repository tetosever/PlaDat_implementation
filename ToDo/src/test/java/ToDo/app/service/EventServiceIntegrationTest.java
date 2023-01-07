package ToDo.app.service;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.domain.Role;
import ToDo.app.domain.Users;
import ToDo.app.repository.DirectoryRepository;
import ToDo.app.repository.EventRepository;
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
public class EventServiceIntegrationTest {

    @Autowired
    private EventService eventService;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DirectoryRepository directoryRepository;
    
    private Directory directory;
    private Users users1;
    private Users users2;
    private Event event1;
    private Event event2;
    private Event event3;
    private List<Event> eventList = new ArrayList<>();
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
        
        event1 = createEvent("Prova1", LocalDate.of(2024, 01, 05), LocalDate.now(), "Bergamo",
            users1, createDirectory("Esame1", directory));
        event1 = eventRepository.save(event1);
        
        event2 = createEvent("Prova1", LocalDate.now(), LocalDate.now(), "Bergamo",
            users1, createDirectory("Esame2", directory));
        event2 = eventRepository.save(event2);

        event3 = createEvent("Prova1", LocalDate.now(), LocalDate.now(), "Milano",
            users2, createDirectory("Esame3", directory));
        event3 = eventRepository.save(event3);

        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);
        
        usersList.add(users1);
        usersList.add(users2);
    }
    
    @After
    public void reset() {
        eventRepository.deleteAll();
        directoryRepository.deleteAll();
        userRepository.deleteAll();
    }
    
    @Test
    public void getAllByFilter_whenOnlyTitleTest() {
        List<Event> eventList = eventService.getAllByFilter("Prova1", null, null);
        assertTrue(eventList.size() == 3);
        assertEquals("Event1 title should be equals", eventList.get(0).getTitle(), "Prova1");
        assertEquals("Event2 title should be equals", eventList.get(1).getTitle(), "Prova1");
        assertEquals("Event3 title should be equals", eventList.get(2).getTitle(), "Prova1");

        assertEquals("Directory name should be equals", eventList.get(0).getDirectory().getName(), "Esame1");
        assertEquals("Directory name should be equals", eventList.get(1).getDirectory().getName(), "Esame2");
        assertEquals("Directory name should be equals", eventList.get(2).getDirectory().getName(), "Esame3");
    }

    @Test
    public void getAllByFilter_whenOnlyStartDateTest() {
        List<Event> eventList = eventService.getAllByFilter(null, LocalDate.now().toString(), null);
        assertTrue(eventList.size() == 3);
        assertEquals("Event1 title should be equals", eventList.get(0).getTitle(), "Prova1");

        assertEquals("Directory name should be equals", eventList.get(0).getDirectory().getName(), "Esame1");
    }

    @Test
    public void getAllByFilter_whenOnlyUserTest() {
        List<Event> eventList = eventService.getAllByFilter(null, null, "Severgnini");
        assertTrue(eventList.size() == 2);
        assertEquals("Event1 title should be equals", eventList.get(0).getTitle(), "Prova1");
        assertEquals("Event2 title should be equals", eventList.get(1).getTitle(), "Prova1");
        
        assertEquals("Directory name should be equals", eventList.get(0).getDirectory().getName(), "Esame1");
        assertEquals("Directory name should be equals", eventList.get(1).getDirectory().getName(), "Esame2");

    }

    @Test
    public void getAllByFilter_whenAllParametersDoesNotFoundTest() {
        List<Event> eventList = eventService.getAllByFilter("Prova1", LocalDate.now().toString(), "Piazza");
        assertTrue(eventList.size() == 0);
    }

    @Test
    public void getAllByFilter_whenAllParametersFoundTest() {
        List<Event> eventList = eventService.getAllByFilter("Prova1", LocalDate.now().toString(), "Severgnini");
        assertTrue(eventList.size() == 1);
        assertEquals("Event1 title should be equals", eventList.get(0).getTitle(), "Prova1");

        assertEquals("Directory name should be equals", eventList.get(0).getDirectory().getName(), "Esame2");
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
