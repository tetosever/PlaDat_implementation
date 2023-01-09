package ToDo.app.validation;

import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class EventValidator extends Validator {

    public void validateEvent(String title, LocalDate start_date, LocalDate end_date, String user_id, String directory_id) {
        validateTitleAndPlace(title);
        validateDuration(start_date, end_date);
        validateDirectoryId(directory_id);
        validateUsersId(user_id);
    }

    private void validateTitleAndPlace(String title){
        if (title == null || title.trim().isEmpty()) {
            throw new ToDoApplicationExceptionBadRequest("Title is null or empty");
        }
    }

    private void validateDuration(LocalDate start_date, LocalDate end_date){
        if (start_date == null ) {
            throw new ToDoApplicationExceptionBadRequest("Start date is not valid");
        }
        if (end_date != null && end_date.isBefore(start_date)) {
            throw new ToDoApplicationExceptionBadRequest("End date should not be before start date");
        }
    }

    private void validateUsersId(String user_id){
        if (user_id == null || user_id.trim().isEmpty()) {
            throw new ToDoApplicationExceptionBadRequest("User id is null or empty");
        }
    }

    private void validateDirectoryId(String directory_id){
        if (directory_id == null || directory_id.trim().isEmpty()) {
            throw new ToDoApplicationExceptionBadRequest("Directory id is null or empty");
        }
    }
}
