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

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteDirectoriesByIdWithView(
            @RequestParam(value = "id") String id){
        directoryService.delete(id);
        return new ModelAndView("redirect:/directories/");
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView addDirectoriesWithView(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "directory_id", required = false) String directory_id) {
        directoryService.create(name, directory_id);
        return new ModelAndView("redirect:/directories/");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateDirectoriesWithView(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "directory_id", required = false) String directory_id){
        directoryService.update(id, name, directory_id);
        return new ModelAndView("redirect:/directories/");
    }
}
