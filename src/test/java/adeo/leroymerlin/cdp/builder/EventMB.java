package adeo.leroymerlin.cdp.builder;

import adeo.leroymerlin.cdp.model.Event;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventMB {
    private Event event;

    public EventMB() {
        event = mock(Event.class);
    }

    public EventMB withId(Long id) {
        when(event.getId()).thenReturn(id);
        return this;
    }

    public EventMB withNbStars(Integer nbStars) {
        when(event.getNbStars()).thenReturn(nbStars);
        return this;
    }

    public EventMB withComment(String comment) {
        when(event.getComment()).thenReturn(comment);
        return this;
    }

    public Event build() {
        return event;
    }
}
