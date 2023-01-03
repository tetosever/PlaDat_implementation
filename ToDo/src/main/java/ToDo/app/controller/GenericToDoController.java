package ToDo.app.controller;

import ToDo.app.service.GenericToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class GenericToDoController {

    @Autowired
    private GenericToDoService genericToDoService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllEventsWithView(){
        ModelAndView view = new ModelAndView("home.html");
        view.addObject("genericToDo", genericToDoService.getAll());
        return view;
    }

}
