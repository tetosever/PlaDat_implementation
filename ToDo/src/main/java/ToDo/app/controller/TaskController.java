package ToDo.app.controller;

import ToDo.app.domain.Event;
import ToDo.app.domain.Task;
import ToDo.app.service.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllTaskWithView(){
        ModelAndView view = new ModelAndView("home.html");
        view.addObject("tasks", taskService.getAll());
        return view;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public List<Task> getAllTasks(){
        return taskService.getAll();
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public Task getTaskById(@PathVariable(value = "id") String id){
        return taskService.getById(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteTaskByIdWithView(
            @PathVariable(value = "id") String id){
        taskService.delete(id);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView addTaskWithView(
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "priority") String priority,
            @RequestParam(value = "user_id", required = false) String user_id,
            @RequestParam(value = "directory_id") String directory_id){
        taskService.create(description, title, priority, user_id, directory_id);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateTaskWithView(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "priority") String priority,
            @RequestParam(value = "user_id") String user_id,
            @RequestParam(value = "directory_id") String directory_id){
        taskService.update(id, description, title, priority, user_id, directory_id);
        return new ModelAndView("redirect:/");
    }

    /*
    @RequestMapping(value = "/events/read/{title}/{start_date}/{user}", method = RequestMethod.GET)
    public ModelAndView getAllByFilter(
        ModelAndView view,
        @PathVariable(value = "title") String title,
        @PathVariable(value = "priority") String priority,
        @PathVariable(value = "user") String name)
    {
        view.addObject("filterTask", taskService.getAllByFilter(title, priority, name));
        return view;
    }
     */

    @ResponseBody
    @RequestMapping(value = "/read/filter", method = RequestMethod.GET)
    public List<Task> getAllByFilterAndId(
        @RequestParam(value = "id", required = false) String id,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "priority", required = false) String priority,
        @RequestParam(value = "user", required = false) String name)
    {
        return taskService.getAllByFilter(id, title, priority, name);
    }
}
