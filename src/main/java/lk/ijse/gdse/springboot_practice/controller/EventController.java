package lk.ijse.gdse.springboot_practice.controller;

import lk.ijse.gdse.springboot_practice.dto.EventDTO;
import lk.ijse.gdse.springboot_practice.entity.Event;
import lk.ijse.gdse.springboot_practice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/event")
@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("create")
    public void saveEvent(@RequestBody EventDTO eventDTO) {
        eventService.saveEvent(eventDTO);
    }

    @GetMapping("all")
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PutMapping("update")
    public void updateEvent(@RequestBody EventDTO eventDTO) {
        eventService.updateEvent(eventDTO);
    }

    @PatchMapping("status/{id}")
    public void changeEventStatus(@PathVariable String id) {
        eventService.changeEventStatus(id);
    }

    @GetMapping("search/{keyword}")
    public List<Event> searchEvent(@PathVariable String keyword) {
        return eventService.searchEvent(keyword);
    }
}
