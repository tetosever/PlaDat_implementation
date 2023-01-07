package ToDo.app.controller;

import ToDo.app.service.EventService;
import ToDo.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class GenericToDoController {
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllEventsWithView(){
        ModelAndView view = new ModelAndView("home.html");
        view.addObject("tasks", taskService.getAll());
        view.addObject("events", eventService.getAll());
        return view;
    }

}
