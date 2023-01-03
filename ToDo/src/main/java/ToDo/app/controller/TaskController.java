package ToDo.app.controller;

import ToDo.app.domain.Task;
import ToDo.app.service.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllDirectories(){
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView addTaskWithView(
            ModelAndView view,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "priority") String priority){
        taskService.create(description, title, priority);
        return view;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteTaskByIdWithView(
            ModelAndView view,
            @RequestParam(value = "id") String id){
        taskService.delete(id);
        return view;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateTaskWithView(
            ModelAndView view,
            @RequestParam(value = "id") String id,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "priority") String priority){
        taskService.update(id, description, title, priority);
        return view;
    }
}
