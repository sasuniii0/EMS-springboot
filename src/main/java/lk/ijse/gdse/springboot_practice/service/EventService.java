package lk.ijse.gdse.springboot_practice.service;

import lk.ijse.gdse.springboot_practice.dto.EventDTO;
import lk.ijse.gdse.springboot_practice.entity.Event;

import java.util.List;

public interface EventService {
    void saveEvent(EventDTO eventDTO);

    List<EventDTO> getAllEvents();

    void updateEvent(EventDTO eventDTO);

    void changeEventStatus(String id);

    List<Event> searchEvent(String keyword);
}
