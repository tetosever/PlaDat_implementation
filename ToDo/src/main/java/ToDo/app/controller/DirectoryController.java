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

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllDirectoriesWithView() {
        ModelAndView view = new ModelAndView("directories.html");
        view.addObject("directories", directoryService.getAll());
        return view;
    }
    
    @RequestMapping(value = "/parent", method = RequestMethod.GET)
    public ModelAndView getParentsDirectoriesWithView(){
        ModelAndView view = new ModelAndView("directories.html");
        //inserisco nella variabile direcotories le direcotory padre.
        view.addObject("parentDirectories", directoryService.getAllParents());
        view.addObject("parentGenericToDo", genericToDoService.getAllByDirectory(null));
        return view;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public List<Directory> getAllDirectories(){
        return directoryService.getAllParents();
    }

    @RequestMapping(value = "/read/forUpdate/{id}", method = RequestMethod.GET)
    public ModelAndView getAllDirectoriesForUpdate(ModelAndView view, @PathVariable(value = "id") String id) {
        view.addObject("directoryListForUpdate", directoryService.getAllForUpdate(id));
        return view;
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    public ModelAndView getDirectoryById(ModelAndView view, @PathVariable(value = "id") String id){
        //viene passato l'id della directory padre, quella a cui sto cercando di accedere per vedere il contenuto
        //inserisco nella variabile presente lato front-end daughter_directory tutte le directory che hanno come padre
        //la directory specificata
        view.addObject("daughter_directory", directoryService.getAllDaughter(id));
        //inserisco nella variabile presente lato front-end genericToDo tutti i genericToDo che hanno come riferimento
        //la directory specificata
        view.addObject("daughter_genericToDo", genericToDoService.getAllByDirectory(id));
        //per recuperare l'elemento specifico, chiameró successivamente il getById che ritornerá task o event
        
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
