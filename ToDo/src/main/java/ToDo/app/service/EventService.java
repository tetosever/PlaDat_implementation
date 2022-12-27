package ToDo.app.service;

import ToDo.app.domain.Event;
import ToDo.app.domain.Users;
import ToDo.app.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private UsersService usersService;

    public List<Event> getAll(){
        return eventRepository.findAll();
    }

    public Event create(Event event){
        checkEvent(event);
        return eventRepository.save(event);
    }

    public void update(Event event){
        checkEvent(event);
        Event savedEvent = eventExists(event);
        savedEvent.setTitle(event.getTitle());
        savedEvent.setStart_date(event.getStart_date());
        savedEvent.setEnd_date(event.getEnd_date());
        savedEvent.setPlace(event.getPlace());
        savedEvent.setDirectory(event.getDirectory());
        savedEvent.setUsersList(event.getUsersList());
        eventRepository.save(savedEvent);
    }

    public void delete(Event event){
        eventRepository.delete(eventExists(event));
    }

    private Event eventExists(Event event){
        Optional<Event> optionalEvent = eventRepository.findById(event.getId());
        if (!optionalEvent.isPresent()){
            // TODO: 27/12/22 eccezione personalizzata
        }
        return optionalEvent.get();
    }

    private void checkEvent(Event event){
        if (event.getStart_date().isAfter(event.getEnd_date())){
            //eccezione BadRequest wrong date
        }

        //check if specific directory is present in db
        directoryService.getByName(event.getDirectory().getName());

        //check if every users in the list are present in db
        for (Users users : event.getUsersList()) {
            if(!usersService.getAll().contains(users)){
                //eccezione Bad Request
            }
        }
        //eventuali altri check, tipo se i parametri sono nulli o meno.
    }
}
