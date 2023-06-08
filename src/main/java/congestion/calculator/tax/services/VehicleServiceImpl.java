package congestion.calculator.tax.services;

import congestion.calculator.tax.entities.Vehicle;
import congestion.calculator.tax.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> getVehiclesByType(String type) {
        return vehicleRepository.findByType(type);
    }
}
