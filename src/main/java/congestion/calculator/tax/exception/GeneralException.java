package congestion.calculator.tax.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GeneralException extends Exception {

    private String message;
    private HttpStatus httpStatus;

    public GeneralException(String message) {
        this.message = message;
    }

    public GeneralException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
