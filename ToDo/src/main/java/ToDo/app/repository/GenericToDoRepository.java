package ToDo.app.repository;

import ToDo.app.domain.Directory;
import ToDo.app.domain.GenericToDo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericToDoRepository extends JpaRepository<GenericToDo, UUID> {

    List<GenericToDo> findByDirectory(Directory directory);
}
