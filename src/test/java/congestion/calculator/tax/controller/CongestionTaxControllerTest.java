package congestion.calculator.tax.controller;

import congestion.calculator.tax.exception.GeneralException;
import congestion.calculator.tax.services.CongestionTaxCalculatorService;
import congestion.calculator.tax.requests.TaxRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.text.ParseException;

import static org.mockito.Mockito.when;

public class CongestionTaxControllerTest {

    private CongestionTaxController controller;

    @Mock
    private CongestionTaxCalculatorService calculator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new CongestionTaxController();
        controller.setCalculator(calculator);
    }

    @Test
    void calculateTax_success() throws GeneralException, ParseException {
        // Arrange
        TaxRequest request = new TaxRequest();
        when(calculator.calculateTax(request)).thenReturn(10);

        // Act
        int result = controller.calculateTax(request);

        // Assert
        Assertions.assertEquals(10, result);
    }

    @Test
    void calculateTax_failure() throws GeneralException, ParseException {
        // Arrange
        TaxRequest request = new TaxRequest();
        when(calculator.calculateTax(request)).thenThrow(new GeneralException("Calculation failed", HttpStatus.BAD_REQUEST));

        // Act and Assert
        Assertions.assertThrows(GeneralException.class, () -> controller.calculateTax(request));
    }
}


