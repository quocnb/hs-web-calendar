package webCalendarSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getTodayEvents() {
        return eventRepository.findEventsByDate(LocalDate.now());
    }

    public List<Event> getEventsBetween(LocalDate fromDate, LocalDate endDate) {
        return eventRepository.findEventsBetweenDates(fromDate, endDate);
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }


    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
