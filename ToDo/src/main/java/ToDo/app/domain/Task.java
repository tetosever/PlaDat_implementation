package ToDo.app.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Task extends GenericToDo{

    private String description;
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
