package ToDo.app.repository;

import ToDo.app.domain.Directory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, UUID> {
    Optional<Directory> findByName(String name);
    
    List<Directory> findByDirectory(Directory directory);
}
