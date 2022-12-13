package ToDo.app.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    // TODO: 13/12/22 verificare che sia effettivamente una many to one e non una one to one come relazione
    @ManyToOne
    @JoinColumn(name = "directory_id", referencedColumnName = "id", nullable = false)
    private Directory directory;

}
