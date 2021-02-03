package kr.co.seok.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class CronConfig {

    private List<String> lists;

    @PostConstruct
    public void onStartup() {
        lists = new ArrayList<>();
        lists.add("5");
        lists.add("2");
        lists.add("1");
        init();
        System.out.println(lists);
    }

//    @Scheduled(initialDelay = 3000)
//    public void initialJob(){
//        init();
//    }

    // 애플리케이션 시작 후 60초 후에 첫 실행, 그 후 매 60초마다 주기적으로 실행한다.
    @Scheduled(fixedDelay = 1000)
    public void otherJob() {
        System.out.println("test" + System.currentTimeMillis());
    }


    public static void init(){
        System.out.println("first");
    }
}
