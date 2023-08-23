package adeo.leroymerlin.cdp.builder;

import adeo.leroymerlin.cdp.model.Band;
import adeo.leroymerlin.cdp.model.Event;

import java.util.Set;

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

    public EventMB withTitle(String title) {
        when(event.getTitle()).thenReturn(title);
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

    public EventMB withBands(Set<Band> bands) {
        when(event.getBands()).thenReturn(bands);
        return this;
    }

    public Event build() {
        return event;
    }
}
