package adeo.leroymerlin.cdp.service;

import adeo.leroymerlin.cdp.exception.EventNotFoundException;
import adeo.leroymerlin.cdp.model.Event;
import adeo.leroymerlin.cdp.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.delete(id);
    }

    public List<Event> getFilteredEvents(String query) {
        return eventRepository.findAllBy().stream()
                .map(e -> constructEvent(query, e))
                .filter(e -> !e.getBands().isEmpty())
                .collect(toList());
    }

    private Event constructEvent(String query, Event e) {
        Event event = new Event();
        event.setId(e.getId());
        event.setTitle(e.getTitle());
        event.setComment(e.getComment());
        event.setImgUrl(e.getImgUrl());
        event.setNbStars(e.getNbStars());
        event.setBands(e.getBandsWithMembersNameContains(query));
        return event;
    }

    public void update(Long id, Event event) {
        Event eventToUpdate = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event [" + id + "] not found"));

        eventToUpdate.update(event.getNbStars(), event.getComment());
        eventRepository.save(eventToUpdate);
    }
}
