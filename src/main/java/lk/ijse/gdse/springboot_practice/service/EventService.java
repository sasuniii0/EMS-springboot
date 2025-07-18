package lk.ijse.gdse.springboot_practice.service;

import lk.ijse.gdse.springboot_practice.dto.EventDTO;
import lk.ijse.gdse.springboot_practice.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    void saveEvent(EventDTO eventDTO);

    List<EventDTO> getAllEvents();

    void updateEvent(EventDTO eventDTO);

    void changeEventStatus(String id);

    List<Event> searchEvent(String keyword);

    Page<Event> getEvents(int page, int size);
}
