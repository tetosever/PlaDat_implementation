package ToDo.app.controller;

import ToDo.app.domain.Event;
import ToDo.app.service.EventService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllEventsWithView(){
        ModelAndView view = new ModelAndView("home.html");
        view.addObject("events", eventService.getAll());
        return view;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public List<Event> getAllEvents(){
        return eventService.getAll();
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public Event getEventById(@PathVariable(value = "id") String id){
        return eventService.getById(id);
    }

    //NB. bisogna capire la strategia da adottare. Se li ricarico i dati nella stessa chiamata o in una separata

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ModelAndView addEventWithView(
            ModelAndView view,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "start_date") LocalDateTime start_date,
            @RequestParam(value = "end_date") LocalDateTime end_date,
            @RequestParam(value = "place") String place){
        eventService.create(title, start_date, end_date, place);
        return view;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateEventWithView(
            ModelAndView view,
            @RequestParam(value = "id") String id,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "start_date") LocalDateTime start_date,
            @RequestParam(value = "end_date") LocalDateTime end_date,
            @RequestParam(value = "place") String place){
        eventService.update(id, title, start_date, end_date, place);
        return view;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteEventByIdWithView(
            ModelAndView view,
            @RequestParam(value = "id") String id,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "start_date") LocalDateTime start_date,
            @RequestParam(value = "end_date") LocalDateTime end_date,
            @RequestParam(value = "place") String place){
        eventService.delete(id);
        return view;
    }
}
