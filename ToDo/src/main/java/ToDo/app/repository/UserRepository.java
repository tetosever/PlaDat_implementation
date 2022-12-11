package ToDo.app.repository;

import ToDo.app.domain.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long>, CostumUserRepository {
    //JpaRepository implement fondamental query to retrieve data from database
    public List<Users> findByName(String name);
}
