package ToDo.app.controller;

import ToDo.app.domain.Event;
import ToDo.app.domain.Task;
import ToDo.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllDirectories(){
        ModelAndView view = new ModelAndView("view-all-users.html");
        view.addObject("directories", taskService.getAll());
        return view;
    }

    //NB. bisogna capire la strategia da adottare. Se li ricarico i dati nella stessa chiamata o in una separata

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(
            ModelAndView view,
            @RequestParam(value = "directory", required = false) Task task){
        taskService.create(task);
        return view;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ModelAndView delete(
            ModelAndView view,
            @RequestParam(value = "directory", required = false) Task task){
        taskService.delete(task);
        return view;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ModelAndView update(
            ModelAndView view,
            @RequestParam(value = "directory", required = false)Task task){
        taskService.update(task);
        return view;
    }
}
