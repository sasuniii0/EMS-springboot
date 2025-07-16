package lk.ijse.gdse.springboot_practice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventDTO {
    private Integer id;
    private String eventName;
    private LocalDate eventDate;
    private LocalDateTime eventBooked;
    private String eventLocation;
    private String eventDescription;
    private String eventStatus;
}
