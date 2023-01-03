package ToDo.app.repository;

import ToDo.app.domain.Event;
import ToDo.app.domain.Users;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    
    @Query(value = "SELECT '*' " +
            "FROM Event event " +
            "INNER JOIN event.usersList " +
            "WHERE event.title LIKE :title AND event.start_date > :startdate AND :usersList IN event.usersList")
    List<Event> findByTitleAndStartDateAndUserList(String title, LocalDateTime startdate, List<Users> usersList);
}
