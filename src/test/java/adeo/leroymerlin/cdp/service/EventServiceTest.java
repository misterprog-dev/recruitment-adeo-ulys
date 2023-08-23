package adeo.leroymerlin.cdp.service;

import adeo.leroymerlin.cdp.builder.BandMB;
import adeo.leroymerlin.cdp.builder.EventMB;
import adeo.leroymerlin.cdp.builder.MemberMB;
import adeo.leroymerlin.cdp.exception.EventNotFoundException;
import adeo.leroymerlin.cdp.model.Band;
import adeo.leroymerlin.cdp.model.Event;
import adeo.leroymerlin.cdp.model.Member;
import adeo.leroymerlin.cdp.repository.EventRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
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
        verify(eventRepository).findById(id);
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

    @Test
    void should_delete_event() {
        // When
        eventService.delete(1L);

        // Then
        verify(eventRepository).delete(1L);
    }

    @Test
    void should_get_filtered_events() {
        // Given
        String query = "Gertrude";
        Set<Member> members1 = new HashSet<>();
        Set<Member> members2 = new HashSet<>();
        Member member1 = new MemberMB().withName("Queen Veronica Graves").build();
        Member member2 = new MemberMB().withName("Queen Gertrude Hudson").build();
        Member member3 = new MemberMB().withName("Queen Danielle Connor (Dannon)").build();

        members1.add(member1);
        members1.add(member2);
        members2.add(member3);

        Set<Band> bands1 = new HashSet<>();
        Set<Band> bands2 = new HashSet<>();
        Band band1 = new BandMB().withName("Sum41").withMembers(members1).build();
        Band band2 = new BandMB().withName("Pink Floyd").withMembers(members2).build();
        bands1.add(band1);
        bands2.add(band2);

        Event event1 = new EventMB().withId(1L).withTitle("Motocultor").withBands(bands1).build();
        Event event2 = new EventMB().withId(2L).withTitle("Download Festival").withBands(bands2).build();
        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);

        when(eventRepository.findAllBy()).thenReturn(events);
        doCallRealMethod().when(member1).hasNameContains(query);
        doCallRealMethod().when(member2).hasNameContains(query);
        doCallRealMethod().when(member3).hasNameContains(query);
        doCallRealMethod().when(band1).getMembersNameContains(query);
        doCallRealMethod().when(band2).getMembersNameContains(query);
        doCallRealMethod().when(event1).getBandsWithMembersNameContains(query);
        doCallRealMethod().when(event2).getBandsWithMembersNameContains(query);

        // When
        List<Event> results = eventService.getFilteredEvents(query);

        // Then
        verify(eventRepository).findAllBy();
        assertThat(results).hasSize(1)
                .extracting(Event::getId, Event::getTitle, Event::bandsName, Event::bandsMembers)
                .containsOnly(Tuple.tuple(1L, "Motocultor [1]", singletonList("Sum41 [1]") , singletonList(member2)));
    }
}
