package ToDo.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Event extends GenericToDo{

    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String place;
    @ManyToOne
    @JoinColumn(name = "directory_id", referencedColumnName = "id", nullable = false)
    private Directory directory;
    @ManyToMany(mappedBy = "eventList")
    private List<Users> usersList;
}
