package congestion.calculator.tax.services;

import java.util.Arrays;
import java.util.List;

import congestion.calculator.tax.entities.City;
import congestion.calculator.tax.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CityServiceTest {

    @MockBean
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    @Test
    public void testGetCityByName() {
        // Arrange
        City city1 = City.builder().cityId(1L).cityName("Stockholm").build();
        City city2 = City.builder().cityId(2L).cityName("Göteborg").build();
        List<City> cities = Arrays.asList(city1, city2);

        // Mock
        when(cityRepository.findByCityName("Stockholm")).thenReturn(Arrays.asList(city1));

        // Act
        List<City> result = cityService.getCityByName("Stockholm");

        // Assert
        assertThat(Arrays.asList(city1)).isEqualTo(result);
    }
}

