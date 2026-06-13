package ee.nikolas.backend0626.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice // automaatselt läheb kõikidele kontrolleritele
public class ControllerAdviceHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(RuntimeException e) {
        ErrorMessage message = new ErrorMessage();
        message.setStatus(HttpStatus.BAD_REQUEST.toString());
        message.setTimestamp(new Date());
        message.setMessage(e.getMessage());
        return ResponseEntity.status(400).body(message);
    }
}
