package ee.nikolas.backend0626.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
    private String status;
    private Date timestamp;
    private String message;
}
