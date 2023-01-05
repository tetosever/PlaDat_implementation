package ToDo.app.domain;

import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GenericToDo {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @NonNull
    @NotBlank
    private String title;
    
    @NotNull
    @ManyToMany(cascade = CascadeType.REFRESH,
            fetch = FetchType.EAGER)
            //mappedBy = "genericToDoList")
    @JoinTable(
            name = "geteric_to_do_users",
            joinColumns = 
            @JoinColumn(name = "generic_to_do_id", referencedColumnName = "id", table = "generic_to_do"),
            inverseJoinColumns = 
            @JoinColumn(name = "users_id", referencedColumnName = "id", table = "users"))
    private List<Users> usersList;

    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "directory_id", referencedColumnName = "id", nullable = false)
    private Directory directory;
}
