package ToDo.app.service;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.domain.Users;
import ToDo.app.exception.ToDoApplicationException;
import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import ToDo.app.repository.EventRepository;
import ToDo.app.validation.EventValidator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            String start_date, 
            String end_date, 
            String place, 
            String user_id, 
            String directory_id) {

        eventValidator.validateId(toUUID(user_id));
        eventValidator.validateId(toUUID(directory_id));
        eventValidator.validateEvent(title, 
                formatterStringToDateTime(start_date), 
                formatterStringToDateTime(end_date), 
                user_id, directory_id);
        
        Event event = new Event();
        event.setTitle(title.trim());
        event.setStart_date(formatterStringToDateTime(start_date));
        event.setEnd_date(formatterStringToDateTime(end_date));
        event.setPlace(place.trim());
        event.setUsersList(new ArrayList<>());
        event.getUsersList().add(usersService.getById(user_id));
        event.setDirectory(directoryService.getById(directory_id));
        
        return eventRepository.save(event);
    }

    public void update(
            String id, 
            String title, 
            String start_date,
            String end_date, 
            String place, 
            String user_id, 
            String directory_id){
        
        UUID uuid = toUUID(id);
        eventValidator.validateId(uuid);
        eventValidator.validateId(toUUID(user_id));
        eventValidator.validateId(toUUID(directory_id));
        eventValidator.validateEvent(title, 
                formatterStringToDateTime(start_date), 
                formatterStringToDateTime(end_date), 
                user_id, directory_id);
        
        Directory newDirectory = directoryService.getById(directory_id);
        Users newUsers = usersService.getById(user_id);
        
        Event savedEvent = eventExists(uuid);
        savedEvent.setTitle(title.trim());
        savedEvent.setStart_date(formatterStringToDateTime(start_date));
        savedEvent.setEnd_date(formatterStringToDateTime(end_date));
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
    
    public List<Event> getAllByFilter(String id, String title, String start_date, String name) {
        List<Event> eventList = new ArrayList<>();

        if (id != null && !id.isEmpty()) {
            eventList.add(getById(id));
            return eventList;
        }
        
        if (title != null && !title.isEmpty()) {
            if (start_date != null && !start_date.isEmpty()) {
                if (name != null && !name.isEmpty()) {
                    eventList = 
                        eventRepository.findByTitleContainsAndStart_dateAfterAndNameIsContaining(
                            title, formatterStringToDateTime(start_date), name);
                }
                else {
                    eventList = eventRepository.findByTitleContainsAndStart_dateAfter(
                        title, formatterStringToDateTime(start_date));
                }
            }
            else {
                if (name != null && !name.isEmpty()) {
                    eventList =
                        eventRepository.findByTitleContainsAndNameIsContaining(title, name);
                }
                else {
                    eventList = eventRepository.findByTitleContains(title);
                }
            }
        }
        else {
            if (start_date != null && !start_date.isEmpty()) {
                if (name != null && !name.isEmpty()) {
                    eventList = eventRepository.findByStart_dateAfterAndNameIsContaining(
                        formatterStringToDateTime(start_date), name);
                }
                else {
                    eventList = eventRepository.findByStart_dateAfter(formatterStringToDateTime(start_date));
                }
            }
            else {
                if (name != null && !name.isEmpty()) {
                    eventList = eventRepository.findByNameIsContaining(name);
                }
            }
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
        UUID uuid = null;
        if (id!=null && id.length()==32)
        {
            id = id.substring(0,8) + "-" + id.substring(8,12) + "-"
                + id.substring(12,16) + "-" + id.substring(16,20) + "-" + id.substring(20);
            if (!UUID_REGEX_PATTERN.matcher(id).matches())
            {
                throw new ToDoApplicationException("UUID error");
            }
            uuid = UUID.fromString(id);
        }
        else if (id.contains("-") && id.length() == 36) {
            uuid = UUID.fromString(id);
        }
        return uuid;
    }
    
    private LocalDate formatterStringToDateTime(String date) {
        LocalDate dateTime = null;
        try {
            if (date != null && !date.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dateTime = LocalDate.parse(date, formatter);
            }
        }
        catch(Exception e) {
            throw new ToDoApplicationExceptionBadRequest(
                "Start Date have wrong format. Should be yyyy-MM-dd !\n" + e.getMessage());
        }
        return dateTime;
    }

}
