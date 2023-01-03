package ToDo.app.service;

import ToDo.app.domain.Event;
import ToDo.app.domain.Priority;
import ToDo.app.domain.Task;
import ToDo.app.domain.Users;
import ToDo.app.exception.ToDoApplicationException;
import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import ToDo.app.repository.TaskRepository;
import ToDo.app.validation.TaskValidator;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private TaskValidator taskValidator;

    public Task getById(String id) {
        UUID uuid = toUUID(id);
        taskValidator.validateId(uuid);

        return taskExists(uuid);
    }

    public List<Task> getAll(){
        return taskRepository.findAll();
    }

    public Task create(String description, String title, String priority){
        taskValidator.validateTask(title, priority);

        Task task = new Task();
        task.setTitle(title);
        task.setPriority(Priority.valueOf(priority));
        task.setDescription(description);
        return taskRepository.save(task);
    }

    public void update(String id, String description, String title, String priority){
        UUID uuid = toUUID(id);
        taskValidator.validateId(uuid);
        taskValidator.validateTask(title, description);

        Task savedTask = taskExists(uuid);
        savedTask.setTitle(title);
        savedTask.setDescription(description);
        savedTask.setPriority(Priority.valueOf(priority));
        //savedTask.setDirectory(task.getDirectory());
        //avedTask.setUsersList(task.getUsersList());
        taskRepository.save(savedTask);
    }

    public void delete(String id){
        UUID uuid = toUUID(id);
        taskValidator.validateId(uuid);

        taskRepository.delete(taskExists(uuid));
    }

    private Task taskExists(UUID uuid){
        Optional<Task> optionalTask = taskRepository.findById(uuid);
        if (!optionalTask.isPresent()){
            throw new ToDoApplicationException("Task is not present in database");
        }
        return optionalTask.get();
    }

    private static UUID toUUID(String id) {
        if (id!=null && id.length()==32)
        {
            id = id.substring(0,8) + "-" + id.substring(8,12) + "-"
                    + id.substring(12,16) + "-" + id.substring(16,20) + "-" + id.substring(20);
            if (!UUID_REGEX_PATTERN.matcher(id).matches())
            {
                throw new ToDoApplicationException("UUID error");
            }
        }
        return UUID.fromString(id);
    }
}
