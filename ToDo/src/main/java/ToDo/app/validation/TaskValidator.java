package ToDo.app.validation;

import ToDo.app.domain.Priority;
import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

@Component
public class TaskValidator extends Validator {

    public void validateTask(String title, String priority){
        validateTitle(title);
        validatePriority(priority);
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
}
