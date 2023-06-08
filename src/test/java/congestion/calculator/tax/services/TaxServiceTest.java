package congestion.calculator.tax.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import congestion.calculator.tax.entities.Tax;
import congestion.calculator.tax.repository.TaxRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TaxServiceTest {

    @MockBean
    private TaxRepository taxRepository;

    @Autowired
    private TaxService taxService;

    @Test
    public void testGetTaxByCityIdAndVehicleId() {
        // Arrange
        Tax tax1 = Tax.builder()
                .taxId(1L)
                .cityId(1L)
                .vehicleId(1L)
                .fee(50)
                .fromTime(LocalTime.of(6, 0))
                .toTime(LocalTime.of(9, 0))
                .build();

        Tax tax2 = Tax.builder()
                .taxId(2L)
                .cityId(1L)
                .vehicleId(1L)
                .fee(75)
                .fromTime(LocalTime.of(9, 0))
                .toTime(LocalTime.of(12, 0))
                .build();

        List<Tax> taxes = Arrays.asList(tax1, tax2);

        // Mock
        Sort.by(Sort.Direction.ASC, "fromTime");
        when(taxRepository.findByCityIdAndVehicleId(1L, 1L, Sort.by(Sort.Direction.ASC, "fromTime"))).thenReturn(Arrays.asList(tax1, tax2));

        // Act
        List<Tax> result = taxService.getTaxByCityIdAndVehicleId(1L, 1L);

        // Assert
        assertThat(result).isEqualTo(taxes);
    }
}
