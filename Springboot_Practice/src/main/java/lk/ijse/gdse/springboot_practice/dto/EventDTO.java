package lk.ijse.gdse.springboot_practice.dto;

import jakarta.validation.constraints.*;
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
    @NotBlank (message = "Event name cannot be blank")
    private String eventName;
    private LocalDate eventDate;
    private LocalDateTime eventBooked;
    @Pattern (regexp = "^[A-Za-z0-9\\s,]+$", message = "Event location can only contain letters, numbers, spaces, and commas")
    @NotBlank (message = "Event location cannot be blank")
    private String eventLocation;
    @NotNull (message = "Event capacity cannot be null")
    @Size(min = 1, message = "Event capacity must be at least 1")
    private String eventDescription;
    @NotEmpty (message = "Event status cannot be empty")
    private String eventStatus;
}
