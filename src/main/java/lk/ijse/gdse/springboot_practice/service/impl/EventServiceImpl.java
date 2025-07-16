package lk.ijse.gdse.springboot_practice.service.impl;

import lk.ijse.gdse.springboot_practice.dto.EventDTO;
import lk.ijse.gdse.springboot_practice.entity.Event;
import lk.ijse.gdse.springboot_practice.repository.EventRepository;
import lk.ijse.gdse.springboot_practice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    @Override
    public void saveEvent(EventDTO eventDTO) {
        eventRepository.save(modelMapper.map(eventDTO, Event.class));
    }
}
