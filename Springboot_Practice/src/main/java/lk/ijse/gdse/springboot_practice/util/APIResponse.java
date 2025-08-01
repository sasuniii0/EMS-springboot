package lk.ijse.gdse.springboot_practice.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {
    private int status;
    private String message;

    // get/create/update method can be returning different types of data
    // so we use Object type for data field
    // e.g. List<EventDTO>, EventDTO, String, etc.
    // This allows flexibility in the response structure
    private Object data;
}
