package congestion.calculator.tax.services;

import congestion.calculator.tax.entities.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> getVehiclesByType(String type);

}
