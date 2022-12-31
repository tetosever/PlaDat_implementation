package ToDo.app.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Directory {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @NonNull
    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "directory_id", referencedColumnName = "id")
    private Directory directory;

}
