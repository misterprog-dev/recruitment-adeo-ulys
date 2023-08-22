package adeo.leroymerlin.cdp.repository;

import adeo.leroymerlin.cdp.model.Event;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface EventRepository extends Repository<Event, Long> {

    void delete(Long eventId);

    List<Event> findAllBy();

    Optional<Event> findById(Long id);

    void save(Event event);
}
