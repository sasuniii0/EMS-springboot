package lk.ijse.gdse.bnauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class APIResponse {
    private int status;
    private String message;
    private Object data;
}
