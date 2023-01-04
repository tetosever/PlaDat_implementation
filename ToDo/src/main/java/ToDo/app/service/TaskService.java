package ToDo.app.service;

import ToDo.app.domain.Directory;
import ToDo.app.domain.Event;
import ToDo.app.domain.Priority;
import ToDo.app.domain.Task;
import ToDo.app.domain.Users;
import ToDo.app.exception.ToDoApplicationException;
import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import ToDo.app.repository.TaskRepository;
import ToDo.app.validation.TaskValidator;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.catalina.User;
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

    public Task create(String description, String title, String priority, String user_id, String directory_id){
        taskValidator.validateTask(title, priority, user_id, directory_id);

        Task task = new Task();
        task.setTitle(title);
        task.setPriority(Priority.valueOf(priority));
        task.setDescription(description);
        task.getUsersList().add(usersService.getById(user_id));
        task.setDirectory(directoryService.getById(directory_id));
        return taskRepository.save(task);
    }

    public void update(
            String id, String description, String title, String priority, String user_id, String directory_id){
        UUID uuid = toUUID(id);
        taskValidator.validateId(uuid);
        taskValidator.validateTask(title, description, user_id, directory_id);

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

    public List<Task> getAllByFilter(String title, Priority priority, List<String> users_id) {
        List<Task> taskList = taskRepository.findAll();
        
        //check if users_id is a valid list and every user_id exist in repository.
        if (checkUsers(usersService.getAll(), users_id) < users_id.size()) {
            throw new ToDoApplicationException("An user of event does not exist");
        }

        //check sui parametri per capire quale ignorare per non distorcere la ricerca
        if (priority != null) {
            taskList = taskList.stream().filter(
                                    task -> task.getPriority().equals(priority))
                            .collect(Collectors.toList());
        } else if (title != null && taskList.size() > 0) {
            taskList = taskList.stream().filter(
                                    task -> task.getTitle().equals(title)
                                            || task.getTitle().contains(title))
                            .collect(Collectors.toList());
        } else if (users_id != null && !users_id.isEmpty() && taskList.size() > 0) {
            taskList = taskList.stream().filter(
                    task -> checkUsers(task.getUsersList(), users_id) > 0).collect(Collectors.toList());
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

    //this method check if every user_id exist in repository.
    private int checkUsers(List<Users> usersList, List<String> users_id) {
        int count = 0;
        if (users_id != null && !users_id.isEmpty()) {
            for (Users user : usersList) {
                for (String user_id : users_id) {
                    UUID uuid = toUUID(user_id);
                    if (uuid.equals(user.getId())) {
                        count++;
                        break;
                    }
                }
            }
        }
        return count;
    }
}
