package appointment.watcher.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends Exception {

    ExceptionType exceptionType;
    String message;

}