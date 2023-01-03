package ToDo.app.validation;

import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import java.util.UUID;

public abstract class Validator {

    public void validateId(UUID id){
        if (id == null){
            throw new ToDoApplicationExceptionBadRequest("UUID is null");
        }
    }

}
