package ToDo.app.service;

import ToDo.app.domain.Event;
import ToDo.app.domain.Users;
import ToDo.app.exception.ToDoApplicationException;
import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import ToDo.app.repository.EventRepository;
import ToDo.app.validation.EventValidator;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;
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

    public Event create(String title, LocalDateTime start_date, LocalDateTime end_date, String place) {
        eventValidator.validateEvent(title, start_date, end_date, place);
        Event event = new Event();
        event.setTitle(title);
        event.setStart_date(start_date);
        event.setEnd_date(end_date);
        event.setPlace(place);
        return eventRepository.save(event);
    }

    public void update(String id, String title, LocalDateTime start_date, LocalDateTime end_date, String place){
        UUID uuid = toUUID(id);
        eventValidator.validateId(uuid);
        eventValidator.validateEvent(title, start_date, end_date, place);
        if (end_date != null && end_date.isBefore(start_date)) {
            throw new ToDoApplicationExceptionBadRequest("End date should not be before start date");
        }
        Event savedEvent = eventExists(uuid);
        savedEvent.setTitle(title);
        savedEvent.setStart_date(start_date);
        savedEvent.setEnd_date(end_date);
        savedEvent.setPlace(place);
        //per il momento mantengo questi, ma c'è bisogno di passarglieli e aggiornare anche questi
        savedEvent.setDirectory(savedEvent.getDirectory());
        savedEvent.setUsersList(savedEvent.getUsersList());
        eventRepository.save(savedEvent);
    }

    public void delete(String id){
        UUID uuid = toUUID(id);
        eventValidator.validateId(uuid);

        eventRepository.delete(eventExists(uuid));
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
}
