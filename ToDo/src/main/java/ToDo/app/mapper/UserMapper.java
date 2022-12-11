package ToDo.app.mapper;

import ToDo.app.domain.Users;
import ToDo.app.repository.CostumUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UserMapper implements CostumUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    //this method return result of costum query that JpaRepository not implements.
    //this case is an example to show to different way of doing the same thing
    @Override
    public List<Users> findByName(String name){
        TypedQuery<Users> query = entityManager.createQuery("SELECT u FROM Users u WHERE u.name = :name", Users.class);
        return query.getResultList();
    }
}
