package lk.ijse.gdse.springboot_practice.service.impl;

import lk.ijse.gdse.springboot_practice.dto.EventDTO;
import lk.ijse.gdse.springboot_practice.entity.Event;
import lk.ijse.gdse.springboot_practice.exception.ResourceAlreadyFoundException;
import lk.ijse.gdse.springboot_practice.exception.ResourceNotFoundException;
import lk.ijse.gdse.springboot_practice.repository.EventRepository;
import lk.ijse.gdse.springboot_practice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    @Override
    public void saveEvent(EventDTO eventDTO) {
        if (eventRepository.existsByEventBooked(eventDTO.getEventBooked())) {
            throw new ResourceAlreadyFoundException("Event with this booked time already exists");
        }
        eventRepository.save(modelMapper.map(eventDTO, Event.class));
    }

    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) {
            throw new ResourceNotFoundException("No events found");
        }
        return modelMapper.map(events, new TypeToken<List<EventDTO>>() {}.getType());
    }

    @Override
    public void updateEvent(EventDTO eventDTO) {
        if (!eventRepository.existsById(eventDTO.getId())) {
            throw new ResourceNotFoundException("Event does not exist");
        }
        if (eventRepository.existsByEventBooked(eventDTO.getEventBooked())) {
            throw new ResourceAlreadyFoundException("Event with this booked time already exists");
        }
        eventRepository.save(modelMapper.map(eventDTO, Event.class));
    }

    @Override
    public void changeEventStatus(String id) {
        if (id.isEmpty()) {
            throw new ResourceNotFoundException("Event id is empty");
        }
        if (!eventRepository.existsById(Integer.parseInt(id))) {
            throw new ResourceNotFoundException("Event with this id does not exist");
        }
        eventRepository.changeEventStatus(id);
    }

    @Override
    public List<Event> searchEvent(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            throw new ResourceNotFoundException("Keyword cannot be null or empty");
        }
        return eventRepository.findEventByEventNameContainingIgnoreCase(keyword);

    }

    @Override
    public Page<Event> getEvents(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page number must be non-negative and size must be positive");
        }
        if (eventRepository.count() == 0) {
            throw new ResourceNotFoundException("No events found");
        }
        Pageable pageable = PageRequest.of(page, size);
        return eventRepository.findAll(pageable);
    }




}
