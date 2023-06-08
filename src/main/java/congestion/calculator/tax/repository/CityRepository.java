package congestion.calculator.tax.repository;

import congestion.calculator.tax.entities.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {

    List<City> findByCityName(String cityName);

}
