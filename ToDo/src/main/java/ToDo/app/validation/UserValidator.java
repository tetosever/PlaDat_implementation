package ToDo.app.validation;

import ToDo.app.domain.Role;
import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import java.util.UUID;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

@Component
public class UserValidator extends Validator{

    public void validateUser(String name, String surname, String role){
        validateName(name, surname);
        validateRole(role);
    }

    private void validateName(String name, String surname){
        if (name == null || name.trim().isEmpty()) {
            throw new ToDoApplicationExceptionBadRequest("Name is null or empty");
        }
        else if (surname == null || surname.trim().isEmpty()) {
            throw new ToDoApplicationExceptionBadRequest("Surname is null or empty");
        }
    }

    private void validateRole(String role){
        if (!EnumUtils.isValidEnum(Role.class, role)) {
            throw new ToDoApplicationExceptionBadRequest("Role is not valid");
        }
    }
}
