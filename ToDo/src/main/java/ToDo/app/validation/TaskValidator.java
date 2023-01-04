package ToDo.app.validation;

import ToDo.app.domain.Priority;
import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

@Component
public class TaskValidator extends Validator {

    public void validateTask(String title, String priority, String user_id, String directory_id){
        validateTitle(title);
        validatePriority(priority);
        validateDirectoryId(directory_id);
        validateUsersId(user_id);
    }

    private void validateTitle(String title){
        if (title.trim() == null || title.trim().isEmpty()) {
            throw new ToDoApplicationExceptionBadRequest("Title is null or empty");
        }
    }

    private void validatePriority(String priority){
        if (!EnumUtils.isValidEnum(Priority.class, priority.trim())) {
            throw new ToDoApplicationExceptionBadRequest("Priority is not valid");
        }
    }
    
    private void validateUsersId(String user_id){
        if (user_id.trim() == null || user_id.trim().isEmpty()) {
            throw new ToDoApplicationExceptionBadRequest("User id is null or empty");
        }
    }

    private void validateDirectoryId(String directory_id){
        if (directory_id.trim() == null || directory_id.trim().isEmpty()) {
            throw new ToDoApplicationExceptionBadRequest("Directory id is null or empty");
        }
    }
}
