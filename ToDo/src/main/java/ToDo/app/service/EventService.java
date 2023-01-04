package ToDo.app.service;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.domain.Users;
import ToDo.app.exception.ToDoApplicationException;
import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import ToDo.app.repository.EventRepository;
import ToDo.app.validation.EventValidator;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private EventValidator eventValidator;

    public Event getById(String id) {
        UUID uuid = toUUID(id);
        eventValidator.validateId(uuid);

        return eventExists(uuid);
    }

    public List<Event> getAll(){
        return eventRepository.findAll();
    }

    public Event create(
            String title, 
            LocalDateTime start_date, 
            LocalDateTime end_date, 
            String place, 
            String user_id, 
            String directory_id) {
        eventValidator.validateEvent(title, start_date, end_date, user_id, directory_id);

        Directory newDirectory = directoryService.getById(directory_id);
        Users newUsers = usersService.getById(user_id);
        
        Event event = new Event();
        event.setTitle(title.trim());
        event.setStart_date(start_date);
        event.setEnd_date(end_date);
        event.setPlace(place.trim());
        if (event.getDirectory().getId() != newDirectory.getId()) {
            event.setDirectory(newDirectory);
        }
        if (!event.getUsersList().contains(newUsers)) {
            event.getUsersList().add(newUsers);
        }
        
        return eventRepository.save(event);
    }

    public void update(
            String id, 
            String title, 
            LocalDateTime start_date, 
            LocalDateTime end_date, 
            String place, 
            String user_id, 
            String directory_id){
        UUID uuid = toUUID(id);
        eventValidator.validateId(uuid);
        eventValidator.validateEvent(title, start_date, end_date, user_id, directory_id);
        
        Directory newDirectory = directoryService.getById(directory_id);
        Users newUsers = usersService.getById(user_id);
        
        Event savedEvent = eventExists(uuid);
        savedEvent.setTitle(title.trim());
        savedEvent.setStart_date(start_date);
        savedEvent.setEnd_date(end_date);
        savedEvent.setPlace(place.trim());
        //per il momento mantengo questi, ma c'è bisogno di passarglieli e aggiornare anche questi
        if (savedEvent.getDirectory().getId() != newDirectory.getId()) {
            savedEvent.setDirectory(newDirectory);
        }
        if (!savedEvent.getUsersList().contains(newUsers)) {
            savedEvent.getUsersList().add(newUsers);
        }
        eventRepository.save(savedEvent);
    }

    public void delete(String id){
        UUID uuid = toUUID(id);
        eventValidator.validateId(uuid);

        eventRepository.delete(eventExists(uuid));
    }

    public List<Event> getAllByFilter(String title, LocalDateTime start_date, List<String> users_id) {
        List<Event> eventList = eventRepository.findAll();

        //posso essere nulli potenzialmente
        //check if users_id is a valid list and every user_id exist in repository.
        if (checkUsers(usersService.getAll(), users_id) < users_id.size()) {
            throw new ToDoApplicationException("An user of event does not exist");
        }

        //check sui parametri per capire quale ignorare per non distorcere la ricerca
        if (title != null) {
            //title is a subsequence of events titles in the repository or is equals 
            eventList = eventList.stream().filter(
                                    event -> event.getTitle().equals(title)
                                            || event.getTitle().contains(title))
                            .collect(Collectors.toList());
        } else if (start_date != null) {
            //start_date is equals or is before to events start_date
            eventList = eventList.stream().filter(
                                    event -> event.getStart_date().isAfter(start_date)
                                            || event.getStart_date().isEqual(start_date))
                            .collect(Collectors.toList());
        } else if (users_id != null && !users_id.isEmpty() && eventList.size() > 0) {
            eventList = eventList.stream().filter(
                    event -> checkUsers(event.getUsersList(), users_id) > 0).collect(Collectors.toList());
        }

        return eventList;
    }

    private Event eventExists(UUID uuid){
        Optional<Event> optionalEvent = eventRepository.findById(uuid);
        if (!optionalEvent.isPresent()) {
            throw new ToDoApplicationException("Event is not present in database");
        }
        return optionalEvent.get();
    }

    private static UUID toUUID(String id) {
        if (id!=null && id.length()==32)
        {
            id = id.substring(0,8) + "-" + id.substring(8,12) + "-"
                    + id.substring(12,16) + "-" + id.substring(16,20) + "-" + id.substring(20);
            if (!UUID_REGEX_PATTERN.matcher(id).matches())
            {
                throw new ToDoApplicationException("UUID error");
            }
        }
        return UUID.fromString(id);
    }

    //this method check if every user_id exist in repository.
    private int checkUsers(List<Users> usersList, List<String> users_id) {
        int count = 0;
        if (users_id != null && !users_id.isEmpty()) {
            for (Users user : usersList) {
                for (String user_id : users_id) {
                    UUID uuid = toUUID(user_id);
                    if (uuid.equals(user.getId())) {
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
    }

}
