package lk.ijse.gdse.springboot_practice.controller;

import lk.ijse.gdse.springboot_practice.dto.EventDTO;
import lk.ijse.gdse.springboot_practice.entity.Event;
import lk.ijse.gdse.springboot_practice.service.EventService;
import lk.ijse.gdse.springboot_practice.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/event")
@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    // ResponseEntity is used to return the response with status code and body
    // APIResponse is a custom response class to standardize the API responses

    @PostMapping("create")
    public ResponseEntity<APIResponse> saveEvent(@RequestBody EventDTO eventDTO) {
        eventService.saveEvent(eventDTO);
        return ResponseEntity.ok(new APIResponse(200, "Success", "Event created successfully"));
    }

    @GetMapping("all")
    public ResponseEntity<APIResponse> getAllEvents() {
        List<EventDTO> eventDTOs = eventService.getAllEvents();
        return ResponseEntity.ok(new APIResponse(200, "Success", eventDTOs));
    }

    @PutMapping("update")
    public ResponseEntity<APIResponse> updateEvent(@RequestBody EventDTO eventDTO) {
        eventService.updateEvent(eventDTO);
        return ResponseEntity.ok(new APIResponse(200, "Success", "Event updated successfully"));
    }

    @PatchMapping("status/{id}")
    public ResponseEntity<APIResponse> changeEventStatus(@PathVariable String id) {
        eventService.changeEventStatus(id);
        return ResponseEntity.ok(new APIResponse(200, "Success", "Event status changed successfully"));
    }

    @GetMapping("search/{keyword}")
    public ResponseEntity<APIResponse> searchEvent(@PathVariable String keyword) {
        List<Event> events = eventService.searchEvent(keyword);
        return ResponseEntity.ok(new APIResponse(200, "Success", events));
    }
    @GetMapping
    public ResponseEntity<APIResponse> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Page<Event> pages = eventService.getEvents(page, size);
        return ResponseEntity.ok(new APIResponse(200, "Success", pages));
    }

}
