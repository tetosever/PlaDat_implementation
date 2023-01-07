package ToDo.app.domain;

import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;
import org.hibernate.annotations.Type;

@Setter
@Getter
@Entity
public class Directory {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @NonNull
    @NotBlank
    private String name;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "directory_id", referencedColumnName = "id")
    private Directory directory;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "directory")
    private List<GenericToDo> genericToDoList;

}
