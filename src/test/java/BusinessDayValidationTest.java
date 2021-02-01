//import net.objectlab.kit.datecalc.common.DateCalculator;
//import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
//import net.objectlab.kit.datecalc.common.HolidayHandlerType;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//
//public class BusinessDayValidationTest {
//    private DateCalculator<LocalDate> dateCalculator;
//    private final LocalDate startDate = LocalDate.of(2009, 12, 23);
//
//    @Before
//    public void setUp() {
//        HashSet<LocalDate> holidays = new HashSet<LocalDate>();
//        holidays.add(LocalDate.of(2009, 12, 25));  // Friday
//
//        DefaultHolidayCalendar<LocalDate> holidayCalendar = new DefaultHolidayCalendar<LocalDate>(holidays);
//
//        LocalDateKitCalculatorsFactory.getDefaultInstance()
//                .registerHolidays("example", holidayCalendar);
//        dateCalculator = LocalDateKitCalculatorsFactory.getDefaultInstance()
//                .getDateCalculator("example", HolidayHandlerType.FORWARD);
//        dateCalculator.setStartDate(startDate);
//    }
//
//    @Test
//    public void should_not_change_calendar_start_date_even_after_moving() {
//        assertThat(dateCalculator.moveByBusinessDays(6).getStartDate(), equalTo(startDate));
//    }
//
//    @Test
//    public void moveByBusinessDays_will_return_24_dec_2009_as_next_business_day() {
//        assertThat(
//                dateCalculator.moveByBusinessDays(1).getCurrentBusinessDate(),
//                equalTo(LocalDate.of(2009, 12, 24)));
//    }
//
//    @Test
//    public void moveByBusinessDays_will_return_28_dec_2009_as_two_business_days_later() {
//        assertThat(
//                dateCalculator.moveByBusinessDays(2).getCurrentBusinessDate(),
//                equalTo(LocalDat.of(2009, 12, 28)));
//
//    }
//
//    @Test
//    public void moveByDays_will_also_return_28_dec_2009_as_two_business_days_later() {
//        assertThat(
//                dateCalculator.moveByDays(2).getCurrentBusinessDate(),
//                equalTo(LocalDate.of(2009, 12, 28)));
//    }
//
//    @Test
//    public void moveByBusinessDays_will_exclude_25_26_and_27_dec_when_computing_business_days() {
//        assertThat(
//                dateCalculator.moveByBusinessDays(5).getCurrentBusinessDate(),
//                equalTo(LocalDate.of(2009, 12, 31)));
//    }
//
//
//    @Test
//    public void moveByDays_will_include_25_26_and_27_dec_when_computing_business_days() {
//        assertThat(
//                dateCalculator.moveByDays(5).getCurrentBusinessDate(),
//                equalTo(LocalDate.of(2009, 12, 28)));
//    }
//}
