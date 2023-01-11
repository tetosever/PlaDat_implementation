package ToDo.app.repository;

import ToDo.app.domain.Priority;
import ToDo.app.domain.Task;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    /*
    @Query(value = "SELECT task " +
        "FROM Task task " +
        "WHERE SUBSTRING(task.title, 1) = :title ")
     */
    @Query(value = "SELECT task " +
        "FROM Task task " +
        "WHERE task.title lIKE CONCAT('%', :title, '%') ")
    List<Task> findByTitleContains(String title);
    
    List<Task> findByPriority(Priority priority);
    
    @Query(value = "SELECT task " +
        "FROM Task task " +
        "JOIN task.usersList user " +
        "WHERE CONCAT(user.name, ' ', user.surname) lIKE CONCAT('%', :name, '%') ")
    List<Task> findByNameIsContaining(String name);

    @Query(value = "SELECT task " +
        "FROM Task task " +
        "WHERE task.title lIKE CONCAT('%', :title, '%')" +
        "AND task.priority = :priority")
    List<Task> findByTitleContainsAndPriority(String title, Priority priority);

    @Query(value = "SELECT task " +
        "FROM Task task " +
        "JOIN task.usersList user " +
        "WHERE task.title lIKE CONCAT('%', :title, '%') " +
        "AND CONCAT(user.name, ' ', user.surname) lIKE CONCAT('%', :name, '%')")
    List<Task> findByTitleContainsAndNameIsContaining(String title, String name);

    @Query(value = "SELECT task " +
        "FROM Task task " +
        "JOIN task.usersList user " +
        "WHERE task.priority = :priority " +
        "AND CONCAT(user.name, ' ', user.surname) lIKE CONCAT('%', :name, '%')")
    List<Task> findByPriorityAndNameIsContaining(Priority priority, String name);

    @Query(value = "SELECT task " +
        "FROM Task task " +
        "JOIN task.usersList user " +
        "WHERE task.title lIKE CONCAT('%', :title, '%') " +
        "AND task.priority = :priority " +
        "AND CONCAT(user.name, ' ', user.surname) lIKE CONCAT('%', :name, '%')")
    List<Task> findByTitleContainsAndPriorityAndNameIsContaining(String title, Priority priority, String name);


}
