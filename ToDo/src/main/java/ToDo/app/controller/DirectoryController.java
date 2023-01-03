package ToDo.app.controller;

import ToDo.app.domain.Directory;
import ToDo.app.service.DirectoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/directories")
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllDirectoriesWithView(){
        ModelAndView view = new ModelAndView("directories.html");
        view.addObject("directories", directoryService.getAll());
        return view;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public List<Directory> getAllDirectories(){
        return directoryService.getAll();
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public Directory getDirectoryById(@RequestParam(value = "id") String id){
        return directoryService.getById(id);
    }

    //NB. bisogna capire la strategia da adottare. Se ricarico i dati nella stessa chiamata o in una separata

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(
            ModelAndView view,
            @RequestParam(value = "name") String name) {
        directoryService.create(name);
        return view;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView delete(
            ModelAndView view,
            @RequestParam(value = "id") String id){
        directoryService.delete(id);
        return view;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView update(
            ModelAndView view,
            @RequestParam(value = "id") String id,
            @RequestParam(value = "name") String name){
        directoryService.update(id, name);
        return view;
    }
}
