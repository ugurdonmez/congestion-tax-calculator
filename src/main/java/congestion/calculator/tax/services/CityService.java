package congestion.calculator.tax.services;

import congestion.calculator.tax.entities.City;

import java.util.List;

public interface CityService {

    List<City> getCityByName(String name);

}
