package ToDo.app.service;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.domain.GenericToDo;
import ToDo.app.domain.Task;
import ToDo.app.exception.ToDoApplicationException;
import ToDo.app.repository.DirectoryRepository;
import ToDo.app.repository.GenericToDoRepository;
import ToDo.app.validation.DirectoryValidator;
import ToDo.app.validation.GenericToDoValidator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericToDoService {

    private final static Pattern UUID_REGEX_PATTERN =
        Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private GenericToDoRepository genericToDoRepository;
    
    @Autowired
    private DirectoryService directoryService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private EventService eventService;
    
    public List<GenericToDo> getAll() {
        return genericToDoRepository.findAll();
    }
    
    public List<GenericToDo> getAllByDirectory(String id) {
        Directory directory = null;
        if (id != null && !id.isEmpty()) {
            directory = directoryService.getById(id);
        }
        return genericToDoRepository.findByDirectory(directory);
    }
    
    //return a event or a task if id is present in event table or in task table
    public <T extends GenericToDo>T getGenericToDoById(String id){
        try{
            Event event = eventService.getById(id);
            return (T) event;
        }
        catch (ToDoApplicationException e){
            try{
                Task task = taskService.getById(id);
                return (T) task;
            }
            catch(ToDoApplicationException exception){
                throw new ToDoApplicationException(
                    "GenericTodo with this id is not present! " + exception.getMessage());
            }
        }
    }
    
}
