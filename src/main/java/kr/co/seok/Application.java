package kr.co.seok;

import com.ibm.icu.util.Calendar;
import kr.co.seok.utils.Holidays;
import kr.co.seok.utils.LunarCalendar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        System.out.println("Calendar.SUNDAY : " + Calendar.SUNDAY);
        System.out.println("Calendar.SATURDAY : " + Calendar.SATURDAY);

        ArrayList<Holidays> arr = LunarCalendar.holidayArray("2021");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }
}