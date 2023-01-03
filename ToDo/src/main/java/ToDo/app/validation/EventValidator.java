package ToDo.app.validation;

import ToDo.app.exception.ToDoApplicationExceptionBadRequest;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class EventValidator extends Validator {

    public void validateEvent(String title, LocalDateTime start_date, LocalDateTime end_date, String place) {
        validateTitleAndPlace(title, place);
        validateDuration(start_date, end_date);
    }

    private void validateTitleAndPlace(String title, String place){
        if (title.trim() == null || title.trim().isEmpty()) {
            throw new ToDoApplicationExceptionBadRequest("Title is null or empty");
        }
        else if (place.trim() == null || place.trim().isEmpty()) {
            throw new ToDoApplicationExceptionBadRequest("Place is null or empty");
        }
    }

    private void validateDuration(LocalDateTime start_date, LocalDateTime end_date){
        if (start_date == null ) {
            throw new ToDoApplicationExceptionBadRequest("Start date is not valid");
        }
    }
}
