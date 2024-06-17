package webCalendarSpring;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.time.LocalDate;

public class EventResponse {
    public enum Action {
        ADD
    }
    private final Action action;
    private final String event;
    private final LocalDate date;

    public EventResponse(Action action, Event eventRequest) {
        this.action = action;
        this.event = eventRequest.getEvent();
        this.date = eventRequest.getDate();
    }

    @JsonGetter("message")
    public String getMessage() {
        return switch (action) {
            case ADD -> "The event has been added!";
        };
    }

    public String getEvent() {
        return event;
    }

    public LocalDate getDate() {
        return date;
    }
}
