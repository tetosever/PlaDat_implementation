package ToDo.app.service;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.domain.Users;
import ToDo.app.exception.ToDoApplicationException;
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

    /*
    public List<Event> getAllByFilter(String title, String start_date_string, String Name) {
        List<Event> eventList = eventRepository.findAll();
        LocalDate start_date = formatterStringToDateTime(start_date_string);

        //posso essere nulli potenzialmente
        //check if users_id is a valid list and every user_id exist in repository.
        if (users_id != null && checkUsers(usersService.getAll(), users_id) < users_id.size()) {
            throw new ToDoApplicationException("An user of event does not exist");
        }

        //check sui parametri per capire quale ignorare per non distorcere la ricerca
        if (title != null) {
            //title is a subsequence of events titles in the repository or is equals 
            eventList = eventList.stream().filter(
                                    event -> event.getTitle().equals(title)
                                            || event.getTitle().toLowerCase().contains(title.toLowerCase()))
                            .collect(Collectors.toList());
        }
        if (start_date != null) {
            //start_date is equals or is before to events start_date
            eventList = eventList.stream().filter(
                                    event -> event.getStart_date().isAfter(start_date)
                                            || event.getStart_date().isEqual(start_date))
                            .collect(Collectors.toList());
        }
        if (users_id != null && !users_id.isEmpty() && eventList.size() > 0) {
            eventList = eventList.stream().filter(
                    event -> checkUsers(event.getUsersList(), users_id) > 0).collect(Collectors.toList());
        }

        return eventList;
    }
    
     */
    
    public List<Event> getAllByFilter(String title, String start_date, String name) {
        List<Event> eventList = new ArrayList<>();
        
        if(title != null){
            if(eventRepository.findByTitleContains(title).isEmpty()) {
                return new ArrayList<>();
            }
            eventList.addAll(eventRepository.findByTitleContains(title));
        }
        if(start_date != null){
            if(eventRepository.findByStart_dateAfter(formatterStringToDateTime(start_date)).isEmpty()) {
                return new ArrayList<>();
            }
            eventList = checkEvents(eventList, eventRepository.findByStart_dateAfter(formatterStringToDateTime(start_date)));
        }
        if(name != null && !name.isEmpty()){
            if(eventRepository.findByNameIsContaining(name).isEmpty()) {
                return new ArrayList<>();
            }
            eventList = checkEvents(eventList, eventRepository.findByNameIsContaining("%" + name + "%"));
        }
        return eventList;
    }
    
    private List<Event> checkEvents(List<Event> eventList1, List<Event> eventList2) {
        if (eventList1 == null || eventList1.isEmpty()) {
            eventList1 = eventList2;
        }
        else {
            for (Event event2 : eventList2) {
                for (Event event1 : eventList1) {
                    if (!event2.getId().equals(event1.getId())){
                        eventList1.add(event2);
                        break;
                    }
                }
            }
        }
        return eventList1;
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
    
    private LocalDate formatterStringToDateTime(String date) {
        LocalDate dateTime = null;
        if (date != null && !date.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dateTime = LocalDate.parse(date, formatter);
        }
        return dateTime;
    }

}
