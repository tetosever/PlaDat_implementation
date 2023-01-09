package ToDo.app.controller;

import ToDo.app.domain.Directory;
import ToDo.app.domain.GenericToDo;
import ToDo.app.service.DirectoryService;
import ToDo.app.service.GenericToDoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/directories")
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;
    
    @Autowired
    private GenericToDoService genericToDoService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getParentsDirectoriesWithView(){
        ModelAndView view = new ModelAndView("directories.html");
        //inserisco nella variabile direcotories le direcotory padre.
        view.addObject("directories", directoryService.getAllParents());
        view.addObject("todos", genericToDoService.getAllByDirectory(null));
        view.addObject("allDirectories", directoryService.getAll());
        view.addObject("directoryListForUpdate", directoryService.getAllForUpdate(null));
        return view;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public List<Directory> getAllDirectories(){
        return directoryService.getAllParents();
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public ModelAndView getDirectoryById(ModelAndView view, @PathVariable(value = "id") String id){
        view.addObject("directories", directoryService.getAllDaughter(id));
        view.addObject("todos", genericToDoService.getAllByDirectory(id));
        view.addObject("allDirectories", directoryService.getAll());
        view.addObject("directoryListForUpdate", directoryService.getAllForUpdate(null));
        return view;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteDirectoriesByIdWithView(
            @PathVariable(value = "id") String id){
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
            @PathVariable(value = "id") String id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "directory_id", required = false) String directory_id){
        directoryService.update(id, name, directory_id);
        return new ModelAndView("redirect:/directories/");
    }
}
