package congestion.calculator.tax.services;

import congestion.calculator.tax.entities.City;
import congestion.calculator.tax.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getCityByName(String name) {
        return cityRepository.findByCityName(name);
    }
}
