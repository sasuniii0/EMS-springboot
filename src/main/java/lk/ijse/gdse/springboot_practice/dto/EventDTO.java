package lk.ijse.gdse.springboot_practice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDTO {
    private Integer id;
    private String eventName;
    private LocalDate eventDate;
    private LocalDateTime eventBooked;
    private String eventLocation;
    private String eventDescription;
    private String eventStatus;
}
