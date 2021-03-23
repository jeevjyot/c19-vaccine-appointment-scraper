package appointment.watcher.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppointmentNotFoundException extends Exception {

    private String message;
}
