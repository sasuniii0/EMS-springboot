package lk.ijse.gdse.springboot_practice.controller;

import lk.ijse.gdse.springboot_practice.dto.EventDTO;
import lk.ijse.gdse.springboot_practice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/event")
@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("create")
    public void saveEvent(@RequestBody EventDTO eventDTO) {
        eventService.saveEvent(eventDTO);
    }
}
