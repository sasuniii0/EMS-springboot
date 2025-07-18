package lk.ijse.gdse.springboot_practice.service.impl;

import lk.ijse.gdse.springboot_practice.dto.EventDTO;
import lk.ijse.gdse.springboot_practice.entity.Event;
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
        eventRepository.save(modelMapper.map(eventDTO, Event.class));
    }

    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return modelMapper.map(events, new TypeToken<List<EventDTO>>() {}.getType());
    }

    @Override
    public void updateEvent(EventDTO eventDTO) {
        eventRepository.save(modelMapper.map(eventDTO, Event.class));
    }

    @Override
    public void changeEventStatus(String id) {
        eventRepository.changeEventStatus(id);
    }

    @Override
    public List<Event> searchEvent(String keyword) {
        return eventRepository.findEventByEventNameContainingIgnoreCase(keyword);

    }

    @Override
    public Page<Event> getEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventRepository.findAll(pageable);
    }


}
