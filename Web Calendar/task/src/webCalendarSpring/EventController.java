package webCalendarSpring;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {

    static Map<String, String> NOT_FOUND_RESPONSE_JSON = Map.of("message", "The event doesn't exist!");

    @Autowired
    private EventService eventService;

    @GetMapping("/today")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> getEventsToday() {
        return eventService.getTodayEvents();
    }

    @GetMapping("")
    public ResponseEntity<?> getEvents(
            @RequestParam(value="start_time", required = false) LocalDate startDate,
            @RequestParam(value="end_time", required = false) LocalDate endDate
    ) {
        List<Event> events;
        if (startDate != null && endDate != null) {
            events = eventService.getEventsBetween(startDate, endDate);
        } else if (startDate == null && endDate == null) {
            events = eventService.getAllEvents();

        } else {
            throw new BadRequestException("");
        }
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(events);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventWithId(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_RESPONSE_JSON);
        }
    }

    @PostMapping("")
    public EventResponse createEvent(@Valid @RequestBody Event event) {
        event = eventService.saveEvent(event);
        return new EventResponse(EventResponse.Action.ADD, event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEventWithId(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        if (event.isPresent()) {
            eventService.deleteEvent(id);
            return ResponseEntity.ok(event.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NOT_FOUND_RESPONSE_JSON);
        }
    }
}
