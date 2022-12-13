package ToDo.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Task extends GenericToDo{

    private LocalDateTime start_date;
    private LocalDateTime end_date;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Lob
    private byte[] image;
    @ManyToOne
    @JoinColumn(name = "directory_id", referencedColumnName = "id", nullable = false)
    private Directory directory;
    @ManyToMany(mappedBy = "taskList")
    private List<Users> usersList;
}
