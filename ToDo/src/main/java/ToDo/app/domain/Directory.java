package ToDo.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
