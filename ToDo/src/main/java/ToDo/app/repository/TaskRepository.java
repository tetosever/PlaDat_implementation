package ToDo.app.repository;

import ToDo.app.domain.Event;
import ToDo.app.domain.Priority;
import ToDo.app.domain.Task;
import ToDo.app.domain.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query(value = "SELECT '*' " +
            "FROM Task task " +
            "INNER JOIN task.usersList " +
            "WHERE task.title LIKE :title AND task.priority > :priority AND :usersList IN task.usersList")
    List<Event> findByTitleAndPriorityAndUserList(String title, Priority priority, List<Users> usersList);
}
