package ToDo.app.repository;

import ToDo.app.domain.Users;
import java.util.List;

public interface CostumUserRepository {
    //this method return result of costum query that JpaRepository not implements
    List<Users> findByName(String name);
}
