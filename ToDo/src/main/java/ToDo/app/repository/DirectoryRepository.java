package ToDo.app.repository;

import ToDo.app.domain.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, String> {
    Optional<Directory> findByName(String name);
}
