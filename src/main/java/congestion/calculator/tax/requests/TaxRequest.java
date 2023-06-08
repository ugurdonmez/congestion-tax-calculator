package congestion.calculator.tax.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class TaxRequest {

    @NotNull(message = "car type can not be null")
    public String vehicleType;
    public String cityName;

    @NotNull(message = "dates can not be null")
    public List<String> dates;
}
