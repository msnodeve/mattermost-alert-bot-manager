package kr.co.seok;

import com.ibm.icu.util.Calendar;
import kr.co.seok.utils.Holidays;
import kr.co.seok.utils.LunarCalendar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.ArrayList;

@SpringBootApplication
@EnableScheduling
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

    // 단일 쓰레드로 우선 코드 작성
    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

//    @Bean
//    public TaskScheduler taskScheduler() {
//        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
//        taskScheduler.setPoolSize(10);
//
//        return taskScheduler;
//    }
}