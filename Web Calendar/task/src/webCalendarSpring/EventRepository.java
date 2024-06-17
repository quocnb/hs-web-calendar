package webCalendarSpring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.date = :date")
    List<Event> findEventsByDate(@Param("date") LocalDate date);

    @Query("SELECT e FROM Event e WHERE e.date BETWEEN :start_date AND :end_date")
    List<Event> findEventsBetweenDates(@Param("start_date") LocalDate startDate, @Param("end_date") LocalDate endDate);
}
