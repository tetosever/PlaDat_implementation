package ToDo.app.controller;

import ToDo.app.domain.GenericToDo;
import ToDo.app.service.EventService;
import ToDo.app.service.GenericToDoService;
import ToDo.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    private GenericToDoService genericToDoService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllEventsWithView(){
        ModelAndView view = new ModelAndView("home.html");
        view.addObject("tasks", taskService.getAll());
        view.addObject("events", eventService.getAll());
        return view;
    }

    //this method insert in variable specific_genericToDo the object Task or Event in base of id value
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ModelAndView getSpecificGenericToDo(ModelAndView view, @PathVariable("id") String id){
        view.addObject("specific_genericToDo", genericToDoService.getGenericToDoById(id));
        // TODO: 08/01/23 bisogna capire come effettuare la visualizzazione 
        return view;
    }

}
