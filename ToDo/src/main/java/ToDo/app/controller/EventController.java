package ToDo.app.controller;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllDirectories(){
        ModelAndView view = new ModelAndView("view-all-users.html");
        view.addObject("directories", eventService.getAll());
        return view;
    }

    //NB. bisogna capire la strategia da adottare. Se li ricarico i dati nella stessa chiamata o in una separata

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(
            ModelAndView view,
            @RequestParam(value = "directory", required = false) Event event){
        eventService.create(event);
        return view;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ModelAndView delete(
            ModelAndView view,
            @RequestParam(value = "directory", required = false)Event event){
        eventService.delete(event);
        return view;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ModelAndView update(
            ModelAndView view,
            @RequestParam(value = "directory", required = false)Event event){
        eventService.update(event);
        return view;
    }
}
