package ToDo.app.repository;

import ToDo.app.domain.Users;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CostumUserRepository {
    //this method return result of costum query that JpaRepository not implements
    List<Users> findByName(String name);
}
