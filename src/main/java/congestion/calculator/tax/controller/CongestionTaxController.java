package congestion.calculator.tax.controller;

import congestion.calculator.tax.exception.GeneralException;
import congestion.calculator.tax.services.CongestionTaxCalculatorService;
import congestion.calculator.tax.requests.TaxRequest;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@RestController
public class CongestionTaxController {

    @Autowired
    private CongestionTaxCalculatorService calculator;

    // TODO: fix valid
    @PostMapping("/calculate")
    public int calculateTax( @RequestBody TaxRequest request) throws ParseException, GeneralException {
        return calculator.calculateTax(request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


    @ExceptionHandler(GeneralException.class)
    public final ResponseEntity<Object> handleGlobalException(GeneralException e) {
        HttpStatus httpStatus = e.getHttpStatus();
        if (null == e.getHttpStatus()) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());
        return new ResponseEntity<>(details, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleException(Exception e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());
        return new ResponseEntity<>(details, httpStatus);
    }
}
