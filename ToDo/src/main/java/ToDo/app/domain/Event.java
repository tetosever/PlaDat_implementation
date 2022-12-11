package ToDo.app.domain;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Event extends GenericToDo{

    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String place;

}
