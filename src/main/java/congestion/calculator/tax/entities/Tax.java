package congestion.calculator.tax.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taxId;

    private Long cityId;

    private Long vehicleId;

    // TODO: big decimal would be better
    private Integer fee;

    private LocalTime fromTime;
    private LocalTime toTime;
}
