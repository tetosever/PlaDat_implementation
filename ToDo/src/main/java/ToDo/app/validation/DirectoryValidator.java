package ToDo.app.validation;

import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import org.springframework.stereotype.Component;

@Component
public class DirectoryValidator extends Validator {

    public void validateName(String name){
        if (name == null || name.trim().isEmpty()) {
            throw new ToDoApplicationExceptionBadRequest("Name is not valid");

        }
    }
}
