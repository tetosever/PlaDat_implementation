package ToDo.app.service;

import ToDo.app.domain.Event;
import ToDo.app.domain.Task;
import ToDo.app.domain.Users;
import ToDo.app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private UsersService usersService;

    public List<Task> getAll(){
        return taskRepository.findAll();
    }

    public Task create(Task task){
        checkTask(task);
        return taskRepository.save(task);
    }

    public void update(Task task){
        checkTask(task);
        Task savedTask = taskExists(task);
        savedTask.setTitle(task.getTitle());
        savedTask.setDescription(task.getDescription());
        savedTask.setPriority(task.getPriority());
        savedTask.setDirectory(task.getDirectory());
        savedTask.setUsersList(task.getUsersList());
        taskRepository.save(savedTask);
    }

    public void delete(Task task){
        taskRepository.delete(taskExists(task));
    }

    private Task taskExists(Task task){
        Optional<Task> optionalTask = taskRepository.findById(task.getId());
        if (!optionalTask.isPresent()){
            // TODO: 27/12/22 eccezione personalizzata
        }
        return optionalTask.get();
    }

    private void checkTask(Task task){
        //check if specific directory is present in db
        directoryService.getById(task.getDirectory().getId());

        //check if every users in the list are present in db
        for (Users users : task.getUsersList()) {
            if(!usersService.getAll().contains(users)){
                //eccezione Bad Request
            }
        }
        //eventuali altri check, tipo se i parametri sono nulli o meno.
    }
}
