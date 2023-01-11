package ToDo.app.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class Users {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @NonNull
    @NotBlank
    private String name;

    @NonNull
    @NotBlank
    private String surname;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    @ManyToMany(mappedBy = "usersList", cascade = CascadeType.REMOVE)
    private List<GenericToDo> genericToDoList;

}
