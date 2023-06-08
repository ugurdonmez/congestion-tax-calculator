package congestion.calculator.tax.helpers;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DateUtils {

    public static boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
        return date1.toLocalDate().isEqual(date2.toLocalDate());
    }

    public static boolean isWeekend(LocalDateTime date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public static boolean isSwedishHoliday(LocalDateTime date) {
        int year = date.getYear();
        // Check for New Year's Day
        if (date.getMonthValue() == 1 && date.getDayOfMonth() == 1) {
            return true;
        }
        // Check for Epiphany
        if (date.getMonthValue() == 1 && date.getDayOfMonth() == 6) {
            return true;
        }
        // Check for Walpurgis Night
        if (date.getMonthValue() == 4 && date.getDayOfMonth() == 30) {
            return true;
        }
        // Check for May Day
        if (date.getMonthValue() == 5 && date.getDayOfMonth() == 1) {
            return true;
        }
        // Check for National Day
        if (date.getMonthValue() == 6 && date.getDayOfMonth() == 6) {
            return true;
        }
        // Check for Midsummer's Eve
        if (date.getMonthValue() == 6 && date.getDayOfMonth() == 23 && year >= 1953) {
            return true;
        }
        // Check for Midsummer's Day
        if (date.getMonthValue() == 6 && date.getDayOfMonth() == 24 && year >= 1953) {
            return true;
        }
        // Check for All Saints' Day
        if (date.getMonthValue() == 11 && date.getDayOfMonth() == 1) {
            return true;
        }
        // Check for Christmas Eve
        if (date.getMonthValue() == 12 && date.getDayOfMonth() == 24) {
            return true;
        }
        // Check for Christmas Day
        if (date.getMonthValue() == 12 && date.getDayOfMonth() == 25) {
            return true;
        }
        // Check for Boxing Day
        if (date.getMonthValue() == 12 && date.getDayOfMonth() == 26) {
            return true;
        }
        // Check for New Year's Eve
        if (date.getMonthValue() == 12 && date.getDayOfMonth() == 31) {
            return true;
        }
        return false;
    }
}
