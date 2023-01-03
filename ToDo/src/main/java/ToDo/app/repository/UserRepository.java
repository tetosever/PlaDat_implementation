package ToDo.app.repository;

import ToDo.app.domain.Users;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID>{
    //JpaRepository implement fondamental query to retrieve data from database
    List<Users> findByName(String name);
}
