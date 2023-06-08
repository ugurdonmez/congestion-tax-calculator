package congestion.calculator.tax.helpers;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class DateUtilsTest {

    @Test
    public void testIsSameDay() {
        LocalDateTime date1 = LocalDateTime.of(2023, 3, 23, 12, 0);
        LocalDateTime date2 = LocalDateTime.of(2023, 3, 23, 15, 0);
        Assertions.assertTrue(DateUtils.isSameDay(date1, date2));
    }

    @Test
    public void testIsNotSameDay() {
        LocalDateTime date1 = LocalDateTime.of(2023, 3, 23, 12, 0);
        LocalDateTime date2 = LocalDateTime.of(2023, 3, 24, 15, 0);
        Assertions.assertFalse(DateUtils.isSameDay(date1, date2));
    }

    @Test
    public void testIsWeekendSaturday() {
        LocalDateTime date = LocalDateTime.of(2023, 3, 25, 12, 0);
        Assertions.assertTrue(DateUtils.isWeekend(date));
    }

    @Test
    public void testIsWeekendSunday() {
        LocalDateTime date = LocalDateTime.of(2023, 3, 26, 12, 0);
        Assertions.assertTrue(DateUtils.isWeekend(date));
    }

    @Test
    public void testIsNotWeekend() {
        LocalDateTime date = LocalDateTime.of(2023, 3, 23, 12, 0);
        Assertions.assertFalse(DateUtils.isWeekend(date));
    }

    @Test
    public void testIsSwedishHoliday() {
        LocalDateTime date = LocalDateTime.of(2023, 1, 1, 12, 0);
        Assertions.assertTrue(DateUtils.isSwedishHoliday(date));
    }

    @Test
    public void testIsNotSwedishHoliday() {
        LocalDateTime date = LocalDateTime.of(2023, 3, 23, 12, 0);
        Assertions.assertFalse(DateUtils.isSwedishHoliday(date));
    }
}

