package congestion.calculator.tax.services;

import congestion.calculator.tax.entities.Tax;
import congestion.calculator.tax.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxServiceImpl implements TaxService {

    @Autowired
    private TaxRepository taxRepository;


    @Override
    public List<Tax> getTaxByCityIdAndVehicleId(long cityId, long vehicleId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "fromTime");
        return taxRepository.findByCityIdAndVehicleId(cityId, vehicleId, sort);
    }
}
