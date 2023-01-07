package ToDo.app.repository;

import ToDo.app.domain.Event;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    
    List<Event> findByTitleContains(String title);
    
    @Query(value = "SELECT event " +
            "FROM Event event " +
            "WHERE event.start_date > :startdate")
    List<Event> findByStart_dateAfter(LocalDate startdate);

    @Query(value = "SELECT event " +
        "FROM Event event " +
        "JOIN event.usersList user " +
        "WHERE :name IN user.name OR :name IN user.surname")
    List<Event> findByNameIsContaining(String name);
}
