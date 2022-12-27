package ToDo.app.controller;

import ToDo.app.domain.Directory;
import ToDo.app.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/directory")
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllDirectories(){
        ModelAndView view = new ModelAndView("view-all-users.html");
        view.addObject("directories", directoryService.getAll());
        return view;
    }

    @RequestMapping(value = "/getByName", method = RequestMethod.GET)
    public ModelAndView getByName(@RequestParam(value = "directory_name", required = false)String name){
        ModelAndView view = new ModelAndView("view-all-users.html");
        view.addObject("directories", directoryService.getAll());
        return view;
    }

    //NB. bisogna capire la strategia da adottare. Se ricarico i dati nella stessa chiamata o in una separata

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(
            ModelAndView view,
            @RequestParam(value = "directory", required = false)Directory directory){
        directoryService.create(directory);
        return view;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ModelAndView delete(
            ModelAndView view,
            @RequestParam(value = "directory", required = false)Directory directory){
        directoryService.delete(directory);
        return view;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ModelAndView update(
            ModelAndView view,
            @RequestParam(value = "directory", required = false)Directory directory){
        directoryService.delete(directory);
        return view;
    }
}
