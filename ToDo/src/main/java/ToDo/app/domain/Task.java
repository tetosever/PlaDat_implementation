package ToDo.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Task extends GenericToDo{

    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private enum priority{
        Low,
        Medium,
        High
    };
    @Lob
    private byte[] image;

}
