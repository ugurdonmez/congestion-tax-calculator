package congestion.calculator.tax.services;

import congestion.calculator.tax.entities.Tax;

import java.util.List;

public interface TaxService {

    List<Tax> getTaxByCityIdAndVehicleId(long cityId, long vehicleId);

}
