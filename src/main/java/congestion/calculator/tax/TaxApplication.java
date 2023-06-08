package congestion.calculator.tax;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@SpringBootApplication
public class TaxApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(DataSource dataSource) {
		return args -> {
			try (Connection conn = dataSource.getConnection()) {
				Statement stmt = conn.createStatement();
				// Create schema

				// Insert data
				stmt.execute("INSERT INTO CITY (CITY_ID, CITY_NAME) VALUES (1, 'stockholm')");
				stmt.execute("INSERT INTO CITY (CITY_ID, CITY_NAME) VALUES (2, 'gothenburg')");

				stmt.execute("INSERT INTO VEHICLE (VEHICLE_ID, IS_TAX_FREE, TYPE) VALUES (1, false, 'car')");
				stmt.execute("INSERT INTO VEHICLE (VEHICLE_ID, IS_TAX_FREE, TYPE) VALUES (2, true, 'bus')");
				stmt.execute("INSERT INTO VEHICLE (VEHICLE_ID, IS_TAX_FREE, TYPE) VALUES (3, true, 'motorcycle')");
				stmt.execute("INSERT INTO VEHICLE (VEHICLE_ID, IS_TAX_FREE, TYPE) VALUES (4, true, 'emergency')");
				stmt.execute("INSERT INTO VEHICLE (VEHICLE_ID, IS_TAX_FREE, TYPE) VALUES (5, true, 'diplomat')");
				stmt.execute("INSERT INTO VEHICLE (VEHICLE_ID, IS_TAX_FREE, TYPE) VALUES (6, true, 'foreign')");
				stmt.execute("INSERT INTO VEHICLE (VEHICLE_ID, IS_TAX_FREE, TYPE) VALUES (7, true, 'military')");


				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (1, 1, 8, '06:00:00', '06:29:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (2, 1, 13, '06:30:00', '06:59:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (3, 1, 18, '07:00:00', '07:59:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (4, 1, 13, '08:00:00', '08:29:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (5, 1, 8, '08:30:00', '14:59:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (6, 1, 13, '15:00:00', '15:29:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (7, 1, 18, '15:30:00', '16:59:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (8, 1, 13, '17:00:00', '17:59:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (9, 1, 8, '18:00:00', '18:29:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (10, 1, 0, '18:30:00', '05:59:00', 1);");

				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (11, 2, 8, '06:00:00', '06:29:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (12, 2, 13, '06:30:00', '06:59:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (13, 2, 18, '07:00:00', '07:59:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (14, 2, 13, '08:00:00', '08:29:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (15, 2, 8, '08:30:00', '14:59:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (16, 2, 13, '15:00:00', '15:29:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (17, 2, 18, '15:30:00', '16:59:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (18, 2, 13, '17:00:00', '17:59:59', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (19, 2, 8, '18:00:00', '18:29:00', 1);");
				stmt.execute("INSERT INTO TAX (TAX_ID, CITY_ID, FEE, FROM_TIME, TO_TIME, VEHICLE_ID) VALUES (20, 2, 0, '18:30:00', '05:59:00', 1);");

			}
		};
	}
}
