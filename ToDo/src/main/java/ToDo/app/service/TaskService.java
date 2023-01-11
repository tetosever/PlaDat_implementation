package ToDo.app.service;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Priority;
import ToDo.app.domain.Task;
import ToDo.app.domain.Users;
import ToDo.app.exception.ToDoApplicationException;
import ToDo.app.repository.TaskRepository;
import ToDo.app.validation.TaskValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Task create(String description, String title, String priority, String user_id, String directory_id){
        taskValidator.validateTask(title, priority, user_id, directory_id);
        
        Task task = new Task();
        task.setTitle(title);
        task.setPriority(Priority.valueOf(priority));
        task.setDescription(description);
        task.setUsersList(new ArrayList<>());
        if (user_id != null) {
            task.getUsersList().add(usersService.getById(user_id));
        }
        if (directory_id != null) {
            task.setDirectory(directoryService.getById(directory_id));
        }
        return taskRepository.save(task);
    }

    public void update(
            String id, String description, String title, String priority, String user_id, String directory_id){
        UUID uuid = toUUID(id);
        taskValidator.validateId(uuid);
        taskValidator.validateId(toUUID(user_id));
        taskValidator.validateId(toUUID(directory_id));
        taskValidator.validateTask(title, priority, user_id, directory_id);

        Directory newDirectory = directoryService.getById(directory_id);
        Users newUsers = usersService.getById(user_id);

        Task savedTask = taskExists(uuid);
        savedTask.setTitle(title.trim());
        savedTask.setDescription(description);
        savedTask.setPriority(Priority.valueOf(priority.trim()));
        if (savedTask.getDirectory().getId() != newDirectory.getId()) {
            savedTask.setDirectory(newDirectory);
        }
        if (!savedTask.getUsersList().contains(newUsers)) {
            savedTask.getUsersList().add(newUsers);
        }
        taskRepository.save(savedTask);
    }

    public void delete(String id){
        UUID uuid = toUUID(id);
        taskValidator.validateId(uuid);

        taskRepository.delete(taskExists(uuid));
    }

    public List<Task> getAllByFilter(String id, String title, String priority, String name) {
        List<Task> taskList = new ArrayList<>();
        
        if (id != null && !id.isEmpty()) {
            taskList.add(getById(id));
            return taskList;
        }

        if (title != null && !title.isEmpty()) {
            if (priority != null && EnumUtils.isValidEnum(Priority.class, priority.trim())) {
                if (name != null && !name.isEmpty()) {
                    taskList = taskRepository.findByTitleContainsAndPriorityAndNameIsContaining(
                        title, Priority.valueOf(priority), name);
                }
                else {
                    taskList = taskRepository.findByTitleContainsAndPriority(title, Priority.valueOf(priority));
                }
            }
            else {
                if (name != null && !name.isEmpty()) {
                    taskList = taskRepository.findByTitleContainsAndNameIsContaining(title, name);
                }
                else {
                    taskList = taskRepository.findByTitleContains(title);
                }
            }
        }
        else {
            if (priority != null && EnumUtils.isValidEnum(Priority.class, priority.trim())) {
                if (name != null && !name.isEmpty()) {
                    taskList = taskRepository.findByPriorityAndNameIsContaining(Priority.valueOf(priority), name);
                }
                else {
                    taskList = taskRepository.findByPriority(Priority.valueOf(priority));
                }
            }
            else {
                if (name != null && !name.isEmpty()) {
                    taskList = taskRepository.findByNameIsContaining(name);
                }
            }
        }
        
        return taskList;
    }

    private Task taskExists(UUID uuid){
        Optional<Task> optionalTask = taskRepository.findById(uuid);
        if (!optionalTask.isPresent()){
            throw new ToDoApplicationException("Task is not present in database");
        }
        return optionalTask.get();
    }

    private static UUID toUUID(String id) {
        UUID uuid = null;
        if (id!=null && id.length()==32)
        {
            id = id.substring(0,8) + "-" + id.substring(8,12) + "-"
                + id.substring(12,16) + "-" + id.substring(16,20) + "-" + id.substring(20);
            if (!UUID_REGEX_PATTERN.matcher(id).matches())
            {
                throw new ToDoApplicationException("UUID error");
            }
            uuid = UUID.fromString(id);
        }
        else if (id.contains("-") && id.length() == 36) {
            uuid = UUID.fromString(id);
        }
        return uuid;
    }
    
}
