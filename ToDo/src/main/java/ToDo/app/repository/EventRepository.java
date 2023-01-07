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

    @Query(value = "SELECT event " +
        "FROM Event event " +
        "WHERE SUBSTRING(event.title, 1) = :title ")
    List<Event> findByTitleContains(String title);
    
    @Query(value = "SELECT event " +
            "FROM Event event " +
            "WHERE event.start_date >= :startdate")
    List<Event> findByStart_dateAfter(LocalDate startdate);

    @Query(value = "SELECT event " +
        "FROM Event event " +
        "JOIN event.usersList user " +
        "WHERE (SUBSTRING(user.name, 1) = :name OR SUBSTRING(user.surname, 1) = :name)")
    List<Event> findByNameIsContaining(String name);

    @Query(value = "SELECT event " +
        "FROM Event event " +
        "WHERE SUBSTRING(event.title, 1) = :title " +
        "AND :startdate >= event.start_date")
    List<Event> findByTitleContainsAndStart_dateAfter(String title, LocalDate startdate);

    @Query(value = "SELECT event " +
        "FROM Event event " +
        "JOIN event.usersList user " +
        "WHERE SUBSTRING(event.title, 1) = :title " +
        "AND (SUBSTRING(user.name, 1) = :name OR SUBSTRING(user.surname, 1) = :name)")
    List<Event> findByTitleContainsAndNameIsContaining(String title, String name);

    @Query(value = "SELECT event " +
        "FROM Event event " +
        "JOIN event.usersList user " +
        "WHERE :startdate >= event.start_date " +
        "AND (SUBSTRING(user.name, 1) = :name OR SUBSTRING(user.surname, 1) = :name)")
    List<Event> findByStart_dateAfterAndNameIsContaining(LocalDate startdate, String name);

    @Query(value = "SELECT event " +
        "FROM Event event " +
        "JOIN event.usersList user " +
        "WHERE SUBSTRING(event.title, 1) = :title " +
        "AND :startdate >= event.start_date " +
        "AND (SUBSTRING(user.name, 1) = :name OR SUBSTRING(user.surname, 1) = :name)")
    List<Event> findByTitleContainsAndStart_dateAfterAndNameIsContaining(String title, LocalDate startdate, String name);


}
