package congestion.calculator.tax.services;

import congestion.calculator.tax.entities.City;
import congestion.calculator.tax.entities.Tax;
import congestion.calculator.tax.entities.Vehicle;
import congestion.calculator.tax.exception.GeneralException;
import congestion.calculator.tax.helpers.DateUtils;
import congestion.calculator.tax.requests.TaxRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.*;
import java.util.stream.Collectors;

@Service
public class CongestionTaxCalculatorService {

    private final String DEFAULT_CITY = "Gothenburg";

    private CityService cityService;
    private VehicleService vehicleService;
    private TaxService taxService;

    @Autowired
    public CongestionTaxCalculatorService(CityService cityService, VehicleService vehicleService, TaxService taxService) {
        this.cityService = cityService;
        this.vehicleService = vehicleService;
        this.taxService = taxService;
    }

    public int calculateTax(TaxRequest req) throws GeneralException, ParseException {

        // get vehicle
        List<Vehicle> vehicles =  this.vehicleService.getVehiclesByType(req.vehicleType);

        if (vehicles.isEmpty()) {
            throw new GeneralException("Vehicle not found", HttpStatus.NOT_FOUND);
        }

        // get city
        if (req.cityName.equals(null) || req.cityName.isEmpty()) {
            req.cityName = this.DEFAULT_CITY;
        }
        List<City> cities = this.cityService.getCityByName(req.cityName);

        if (cities.isEmpty()) {
            throw new GeneralException("City not found", HttpStatus.NOT_FOUND);
        }

        // list should include one city or vehicle
        // TODO: make names PK
        Vehicle vehicle = vehicles.get(0);
        City city = cities.get(0);

        // check tax free vehicle
        if (vehicle.isTaxFree()) {
            return 0;
        }

        // get fees from db
        List<Tax> taxes = this.taxService.getTaxByCityIdAndVehicleId(city.getCityId(), vehicle.getVehicleId());

        return calculate(taxes, req.dates);
    }

    private int calculate(List<Tax> taxes, List<String> dates) throws ParseException {

        if (dates == null || dates.isEmpty()) {
            return 0;
        }

        var sortedDates = singleChargeRule(dates, taxes);

        int total = 0;
        LocalDateTime prevDate = null;
        int localTotal = 0;

        for (int i = 0; i < sortedDates.size() ; i++) {
            LocalDateTime currDate = sortedDates.get(i);

            if (DateUtils.isSwedishHoliday(currDate) || DateUtils.isWeekend(currDate)) {
                if (localTotal > 60) {
                    total += 60;
                } else {
                    total += localTotal;
                }

                localTotal = 0;
                prevDate = currDate;
            } else {
                int fee = getCharge(currDate, taxes);

                if (prevDate == null) {
                    localTotal = fee;
                    prevDate = currDate;
                } else {
                    if (DateUtils.isSameDay(prevDate, currDate)) {
                        localTotal += fee;
                    } else {
                        if (localTotal > 60) {
                            total += 60;
                        } else {
                            total += localTotal;
                        }

                        localTotal = fee;
                        prevDate = currDate;
                    }
                }
            }
        }

        if (localTotal > 60) {
            total += 60;
        } else {
            total += localTotal;
        }

        return total;
    }

    private static List<LocalDateTime> singleChargeRule(List<String> dates, List<Tax> taxes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Sort the dates in ascending order
        List<LocalDateTime> sortedDates = dates.stream()
                .map(date -> LocalDateTime.parse(date, formatter))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        List<LocalDateTime> filteredDates = new ArrayList<>();
        LocalDateTime lastDate = null;
        int maxCharge = 0;

        for (LocalDateTime date : sortedDates) {
            // check if the date is within 60 minutes of the last date
            if (lastDate != null && lastDate.plusMinutes(60).isAfter(date)) {
                // if so, update the maxCharge if necessary
                int charge = getCharge(date, taxes);
                if (charge > maxCharge) {
                    maxCharge = charge;
                }
            } else {
                // if not, add the last date to the filtered list with the maxCharge
                if (lastDate != null) {
                    filteredDates.add(lastDate);
                }
                // start a new interval with the current date
                maxCharge = getCharge(date, taxes);
            }
            lastDate = date;
        }
        // add the last date to the filtered list with the maxCharge
        if (lastDate != null) {
            filteredDates.add(lastDate);
        }
        return filteredDates;
    }

    private static int getCharge(LocalDateTime date, List<Tax> taxes) {
        LocalTime timeToCheck = date.toLocalTime();
        for (Tax tax : taxes) {
            if (isTimeWithinRange(timeToCheck, tax.getFromTime(), tax.getToTime())) {
                return tax.getFee();
            }
        }
        throw new IllegalArgumentException("No tax found for the specified date and time.");
    }

    private static boolean isTimeWithinRange(LocalTime timeToCheck, LocalTime fromTime, LocalTime toTime) {
        if (fromTime.isBefore(toTime)) {
            return !timeToCheck.isBefore(fromTime) && !timeToCheck.isAfter(toTime);
        } else if (fromTime.isAfter(toTime)) {
            return !timeToCheck.isBefore(fromTime) || !timeToCheck.isAfter(toTime);
        } else {
            return timeToCheck.equals(fromTime);
        }
    }
}

