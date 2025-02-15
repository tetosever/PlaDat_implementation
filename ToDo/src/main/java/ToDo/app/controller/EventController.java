package ToDo.app.controller;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.domain.Task;
import ToDo.app.domain.Users;
import ToDo.app.service.EventService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteEventByIdWithView(
            @PathVariable(value = "id") String id){
        eventService.delete(id);
        return new ModelAndView("redirect:/");
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView addEventWithView(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "start_date") String start_date,
            @RequestParam(value = "end_date", required = false) String end_date,
            @RequestParam(value = "place") String place,
            @RequestParam(value = "user_id") String user_id,
            @RequestParam(value = "directory_id") String directory_id){
        eventService.create(title, start_date, end_date, place, user_id, directory_id);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateEventWithView(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "start_date") String start_date,
            @RequestParam(value = "end_date", required = false) String end_date,
            @RequestParam(value = "place") String place,
            @RequestParam(value = "user_id") String user_id,
            @RequestParam(value = "directory_id") String directory_id){
        eventService.update(id, title, start_date, end_date, place, user_id, directory_id);
        return new ModelAndView("redirect:/");
    }

    /*
    //bisogna passare le variabili thymeleaf al metodo che poi tornerá la view reimpostando i valori originali
    @RequestMapping(value = "/events/read/{id}/{title}/{start_date}/{user}", method = RequestMethod.GET)
    public ModelAndView getAllByFilter(
            @ModelAttribute(value = "tasks") List<Task> taskList,
            @ModelAttribute(value = "users") List<Users> usersList,
            @ModelAttribute(value = "directories") List<Directory> directoryList,
            @PathVariable(value = "id") String id,
            @PathVariable(value = "title") String title,
            @PathVariable(value = "start_date") String start_date,
            @PathVariable(value = "user") String name)
    {
        ModelAndView view = new ModelAndView("home.html");
        view.addObject("tasks", taskList);
        view.addObject("users", usersList);
        view.addObject("directories", directoryList);
        view.addObject("events", eventService.getAllByFilter(id, title, start_date, name));
        return view;
    }
    
     */
    
    @ResponseBody
    @RequestMapping(value = "/read/filter", method = RequestMethod.GET)
    public List<Event> getAllByFilter(
        @RequestParam(value = "id", required = false) String id,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "start_date", required = false) String start_date,
        @RequestParam(value = "user", required = false) String name)
    {
        return eventService.getAllByFilter(id, title, start_date, name);
    }
    
}
