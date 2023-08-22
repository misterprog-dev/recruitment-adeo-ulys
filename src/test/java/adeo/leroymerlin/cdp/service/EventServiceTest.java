package adeo.leroymerlin.cdp.service;

import adeo.leroymerlin.cdp.builder.EventMB;
import adeo.leroymerlin.cdp.exception.EventNotFoundException;
import adeo.leroymerlin.cdp.model.Event;
import adeo.leroymerlin.cdp.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class EventServiceTest {
    private EventRepository eventRepository;
    private EventService eventService;

    @BeforeEach
    void setup() {
        eventRepository = mock(EventRepository.class);
        eventService = new EventService(eventRepository);
    }

    @Test
    void should_throw_when_update_event_with_event_dont_exists() {
        // Given
        Long id = 1L;
        when(eventRepository.findById(id)).thenReturn(empty());

        // When
        Exception exception = assertThrows(EventNotFoundException.class, () -> eventService.update(id, new Event()));

        // Then
        assertEquals("Event [" + id + "] not found", exception.getMessage());
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void should_update_event() {
        // Given
        Long id = 1L;
        Event existingEvent = new EventMB().withId(id).withNbStars(2).withComment("last comment").build();
        Event newEvent = new EventMB().withId(id).withNbStars(2).withComment("new comment").build();
        when(eventRepository.findById(id)).thenReturn(of(existingEvent));

        // When
        eventService.update(id, newEvent);

        // Then
        verify(eventRepository).findById(id);
        verify(existingEvent).update(newEvent.getNbStars(), newEvent.getComment());
        verify(eventRepository).save(existingEvent);
    }
}
