package kr.co.seok.config;

import kr.co.seok.dto.MatterMostGroup;
import kr.co.seok.retrofit.RetrofitClient;
import kr.co.seok.retrofit.dto.Attachments;
import kr.co.seok.retrofit.dto.MatterMostRequestDto;
import kr.co.seok.service.FileService;
import kr.co.seok.service.GroupService;
import kr.co.seok.service.NotificationService;
import kr.co.seok.service.UrlService;
import kr.co.seok.utils.Holidays;
import kr.co.seok.utils.LunarCalendar;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class CronConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<MatterMostGroup> matterMostGroupLists;

    @Autowired
    private FileService fileService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UrlService urlService;

    // 날짜 관련
    private Date date;
    private Calendar calendar;
    private DateTimeFormatter dateTimeFormatter;

    // 휴일 관련
    private ArrayList<Holidays> holidays;

    @SneakyThrows
    @PostConstruct
    public void onStartup() {
        matterMostGroupLists = groupService.loadAll("");
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        date = new Date(dateTimeFormatter.format(LocalDateTime.now()));
        calendar = Calendar.getInstance(Locale.KOREA);
        calendar.setTime(date);
        holidays = LunarCalendar.holidayArray(Integer.toString(calendar.get(Calendar.YEAR)));
    }

    // 매년 휴일 새로 변경
    @Scheduled(cron = "1 0 0 1 1 ?")
    public void refreshHolyDay() {
        holidays = LunarCalendar.holidayArray(Integer.toString(calendar.get(Calendar.YEAR)));
    }

    // 매일 0시 0분 0초가 되면 날짜를 새로 변경
    @Scheduled(cron = "0 0 0 * * *")
    public void refreshDate() {
        date = new Date(dateTimeFormatter.format(LocalDateTime.now()));
        calendar.setTime(date);
    }

    // 매 55초가 되면 db에 있는 모든 내용 들고와서 refresh
    @SneakyThrows
    @Scheduled(cron = "55 * * * * *")
    public void loadAllGroup() {
        matterMostGroupLists = groupService.loadAll("");
    }

    // 매분 20초가 되면 휴일인지 아닌지 검사 후
    // 휴일이면 pass
    // 휴일이 아니면 메세지 전송
    @SneakyThrows
    @Scheduled(cron = "20 * * * * *")
    public void sendMessage() {
        String time = (calendar.get(Calendar.HOUR_OF_DAY) + 9) + ":" + calendar.get(Calendar.MINUTE);
        logger.info(time);
        if (!isHolyDay()) {
            for (MatterMostGroup matterMostGroup : matterMostGroupLists) {
                logger.info(matterMostGroup.toString());
                if (matterMostGroup.getTime().equals(time)) {
                    MatterMostRequestDto matterMostRequestDto = new MatterMostRequestDto();
                    Attachments attachments = matterMostRequestDto.getAttachments()[0];
                    attachments.setText(matterMostGroup.getMatterMostNotification().getMessage());
//                    attachments.setImage_url("http://t4coach33.p.ssafy.io/images/6.gif");
                    matterMostRequestDto.setAttachments(matterMostRequestDto.getAttachments());
                    Call<String> sendMessageCall = RetrofitClient.getSendMessageService().sendMessage(matterMostGroup.getMatterMostUrl().getUrl(), matterMostRequestDto);
                    sendMessageCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            logger.info(this.getClass().toString() + " >>> " + response.body());
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            logger.error(this.getClass().toString() + " >>> " + t.getMessage());
                        }
                    });
                }
            }
        }
    }

    private boolean isHolyDay() {
        date = new Date(dateTimeFormatter.format(LocalDateTime.now()));
        calendar.setTime(date);
        String todayDate = calendar.get(Calendar.YEAR) + String.format("%02d", calendar.get(Calendar.MONTH)) + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
        for (Holidays holiday : holidays) {
            String holidayDate = holiday.getYear() + holiday.getDate();
            if (holidayDate.equals(todayDate)) {
                return true;
            }
        }
        return calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7;
    }
}
