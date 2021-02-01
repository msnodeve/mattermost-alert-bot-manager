package kr.co.seok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LocalDate today = LocalDate.of(2020, 2, 1);

        // Add one holiday for testing
        List<LocalDate> holidays = new ArrayList<>();
        holidays.add(LocalDate.of(2020, 2, 11));
        holidays.add(LocalDate.of(2020, 2, 12));
        holidays.add(LocalDate.of(2020, 2, 13));
        holidays.add(LocalDate.of(2020, 2, 14));
        holidays.add(LocalDate.of(2020, 2, 15));

        System.out.println(countBusinessDaysBetween(today, today.plusDays(20), Optional.empty()));
        System.out.println(countBusinessDaysBetween(today, today.plusDays(20), Optional.of(holidays)));
    }

    private static long countBusinessDaysBetween(LocalDate startDate, LocalDate endDate,
                                                 Optional<List<LocalDate>> holidays) {
        if (startDate == null || endDate == null || holidays == null) {
            throw new IllegalArgumentException("Invalid method argument(s) to countBusinessDaysBetween(" + startDate
                    + "," + endDate + "," + holidays + ")");
        }

        Predicate<LocalDate> isHoliday = date -> holidays.isPresent() ? holidays.get().contains(date) : false;

        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        long businessDays = Stream.iterate(startDate, date -> date.plusDays(1)).limit(daysBetween)
                .filter(isHoliday.or(isWeekend).negate()).count();
        return businessDays;
    }
}