package ToDo.app.validation;

import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import java.util.UUID;

public abstract class Validator {

    // TODO: 09/01/23 mettere il metodo abstract e specificare quale oggetto ha id sbagliato 
    public void validateId(UUID id){
        if (id == null){
            throw new ToDoApplicationExceptionBadRequest("UUID is null");
        }
    }

}
