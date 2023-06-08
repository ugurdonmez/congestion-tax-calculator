package congestion.calculator.tax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import congestion.calculator.tax.entities.City;
import congestion.calculator.tax.entities.Tax;
import congestion.calculator.tax.entities.Vehicle;
import congestion.calculator.tax.exception.GeneralException;
import congestion.calculator.tax.requests.TaxRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class CongestionTaxCalculatorServiceTest {

    @Mock
    private CityService cityService;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private TaxService taxService;

    @InjectMocks
    private CongestionTaxCalculatorService congestionTaxCalculatorService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateTax_whenVehicleNotFound_throwGeneralException() throws ParseException {
        // Arrange
        String vehicleType = "Car";
        TaxRequest taxRequest = new TaxRequest();
        taxRequest.setVehicleType(vehicleType);
        List<Vehicle> vehicles = Arrays.asList();
        when(vehicleService.getVehiclesByType(vehicleType)).thenReturn(vehicles);

        // Act and assert
        Assertions.assertThrows(GeneralException.class, () -> {
            congestionTaxCalculatorService.calculateTax(taxRequest);
        });
    }

    @Test
    void testCalculateTax_whenCityNotFound_throwGeneralException() throws ParseException {
        // Arrange
        String vehicleType = "Car";
        String cityName = "Gothenburg";
        TaxRequest taxRequest = new TaxRequest();
        taxRequest.setVehicleType(vehicleType);
        taxRequest.setCityName(cityName);
        Vehicle vehicle = Vehicle.builder().vehicleId(1L).type(vehicleType).isTaxFree(false).build();
        List<Vehicle> vehicles = Arrays.asList(vehicle);
        when(vehicleService.getVehiclesByType(vehicleType)).thenReturn(vehicles);
        when(cityService.getCityByName(cityName)).thenReturn(Arrays.asList());

        // Act and assert
        Assertions.assertThrows(GeneralException.class, () -> {
            congestionTaxCalculatorService.calculateTax(taxRequest);
        });
    }

    @Test
    void testCalculateTax_whenVehicleIsTaxFree_returnZero() throws ParseException, GeneralException {
        // Arrange
        String vehicleType = "Bus";
        String cityName = "Gothenburg";
        TaxRequest taxRequest = new TaxRequest();
        taxRequest.setVehicleType(vehicleType);
        taxRequest.setCityName(cityName);
        Vehicle vehicle = Vehicle.builder().vehicleId(1L).type(vehicleType).isTaxFree(true).build();
        List<Vehicle> vehicles = Arrays.asList(vehicle);
        City city = City.builder().cityId(1L).cityName(cityName).build();
        List<City> cities = Arrays.asList(city);
        when(vehicleService.getVehiclesByType(vehicleType)).thenReturn(vehicles);
        when(cityService.getCityByName(cityName)).thenReturn(cities);

        // Act
        int result = congestionTaxCalculatorService.calculateTax(taxRequest);

        // Assert
        assertEquals(0, result);
    }

    void testCalculateTax_whenValidRequest_returnTax() throws ParseException, GeneralException {
        // create mock objects
        CityService cityService = mock(CityService.class);
        VehicleService vehicleService = mock(VehicleService.class);
        TaxService taxService = mock(TaxService.class);

        // create test object
        CongestionTaxCalculatorService calculatorService = new CongestionTaxCalculatorService(cityService, vehicleService, taxService);

        // mock the dependencies
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(Vehicle.builder().vehicleId(1L).type("Car").isTaxFree(false).build());
        when(vehicleService.getVehiclesByType("Car")).thenReturn(vehicles);

        List<City> cities = new ArrayList<>();
        cities.add(City.builder().cityId(1L).cityName("Gothenburg").build());
        when(cityService.getCityByName("Gothenburg")).thenReturn(cities);

        List<Tax> taxes = new ArrayList<>();
        taxes.add(Tax.builder().taxId(1L).cityId(1L).vehicleId(1L).fee(10).fromTime(LocalTime.of(6, 0)).toTime(LocalTime.of(7, 0)).build());
        when(taxService.getTaxByCityIdAndVehicleId(1L, 1L)).thenReturn(taxes);

        // create test request
        TaxRequest request = new TaxRequest();
        request.vehicleType = "Car";
        request.cityName = "Gothenburg";
        request.dates = Arrays.asList("2022-03-23 06:30:00", "2022-03-23 07:30:00");

        // call the method under test
        int result = calculatorService.calculateTax(request);

        // assert the result
        assertEquals(10, result);
    }

    void testCalculateTax_whenValidRequest_returnTax_testSingleChargeRule() throws ParseException, GeneralException {
        // create mock objects
        CityService cityService = mock(CityService.class);
        VehicleService vehicleService = mock(VehicleService.class);
        TaxService taxService = mock(TaxService.class);

        // create test object
        CongestionTaxCalculatorService calculatorService = new CongestionTaxCalculatorService(cityService, vehicleService, taxService);

        // mock the dependencies
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(Vehicle.builder().vehicleId(1L).type("Car").isTaxFree(false).build());
        when(vehicleService.getVehiclesByType("Car")).thenReturn(vehicles);

        List<City> cities = new ArrayList<>();
        cities.add(City.builder().cityId(1L).cityName("Gothenburg").build());
        when(cityService.getCityByName("Gothenburg")).thenReturn(cities);

        List<Tax> taxes = new ArrayList<>();
        taxes.add(Tax.builder().taxId(1L).cityId(1L).vehicleId(1L).fee(8).fromTime(LocalTime.of(6, 0)).toTime(LocalTime.of(6, 29)).build());
        taxes.add(Tax.builder().taxId(1L).cityId(1L).vehicleId(1L).fee(13).fromTime(LocalTime.of(6, 30)).toTime(LocalTime.of(6, 59)).build());
        taxes.add(Tax.builder().taxId(1L).cityId(1L).vehicleId(1L).fee(18).fromTime(LocalTime.of(7, 0)).toTime(LocalTime.of(7, 59)).build());

        when(taxService.getTaxByCityIdAndVehicleId(1L, 1L)).thenReturn(taxes);

        // create test request
        TaxRequest request = new TaxRequest();
        request.vehicleType = "Car";
        request.cityName = "Gothenburg";
        request.dates = Arrays.asList("2022-03-23 06:15:00", "2022-03-23 06:40:00");

        // call the method under test
        int result = calculatorService.calculateTax(request);

        // assert the result
        assertEquals(13, result);
    }

    void testCalculateTax_whenValidRequest_returnTax_testMaxFeeDaily() throws ParseException, GeneralException {
        // create mock objects
        CityService cityService = mock(CityService.class);
        VehicleService vehicleService = mock(VehicleService.class);
        TaxService taxService = mock(TaxService.class);

        // create test object
        CongestionTaxCalculatorService calculatorService = new CongestionTaxCalculatorService(cityService, vehicleService, taxService);

        // mock the dependencies
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(Vehicle.builder().vehicleId(1L).type("Car").isTaxFree(false).build());
        when(vehicleService.getVehiclesByType("Car")).thenReturn(vehicles);

        List<City> cities = new ArrayList<>();
        cities.add(City.builder().cityId(1L).cityName("Gothenburg").build());
        when(cityService.getCityByName("Gothenburg")).thenReturn(cities);

        List<Tax> taxes = new ArrayList<>();
        taxes.add(Tax.builder().taxId(1L).cityId(1L).vehicleId(1L).fee(80).fromTime(LocalTime.of(6, 0)).toTime(LocalTime.of(6, 29)).build());
        taxes.add(Tax.builder().taxId(1L).cityId(1L).vehicleId(1L).fee(13).fromTime(LocalTime.of(6, 30)).toTime(LocalTime.of(6, 59)).build());
        taxes.add(Tax.builder().taxId(1L).cityId(1L).vehicleId(1L).fee(18).fromTime(LocalTime.of(7, 0)).toTime(LocalTime.of(7, 59)).build());
        taxes.add(Tax.builder().taxId(1L).cityId(1L).vehicleId(1L).fee(18).fromTime(LocalTime.of(8, 0)).toTime(LocalTime.of(8, 59)).build());

        when(taxService.getTaxByCityIdAndVehicleId(1L, 1L)).thenReturn(taxes);

        // create test request
        TaxRequest request = new TaxRequest();
        request.vehicleType = "Car";
        request.cityName = "Gothenburg";
        request.dates = Arrays.asList("2022-03-23 06:15:00", "2022-03-23 08:40:00");

        // call the method under test
        int result = calculatorService.calculateTax(request);

        // assert the result
        assertEquals(60, result);
    }


}
