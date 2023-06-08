package congestion.calculator.tax.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import congestion.calculator.tax.entities.Vehicle;
import congestion.calculator.tax.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class VehicleServiceTest {

    @MockBean
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    @Test
    public void testGetVehiclesByType() {
        // Arrange
        Vehicle vehicle1 = Vehicle.builder().vehicleId(1L).type("Car").isTaxFree(false).build();
        Vehicle vehicle2 = Vehicle.builder().vehicleId(2L).type("Truck").isTaxFree(false).build();
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

        // Mock
        when(vehicleRepository.findByType("Car")).thenReturn(Arrays.asList(vehicle1));

        // Act
        List<Vehicle> result = vehicleService.getVehiclesByType("Car");

        // Assert
        assertThat(result).isEqualTo(Arrays.asList(vehicle1));
    }
}
