package congestion.calculator.tax.repository;

import congestion.calculator.tax.entities.Tax;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxRepository extends CrudRepository<Tax, Long> {

    List<Tax> findByCityIdAndVehicleId(Long cityId, Long vehicleId, Sort sort);

}
