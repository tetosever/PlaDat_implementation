package ToDo.app.mapper;

import ToDo.app.domain.User;
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
    public List<User> findByName(String name){
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class);
        return query.getResultList();
    }
}
