package kr.co.seok.config;

import kr.co.seok.dto.MatterMostGroup;
import kr.co.seok.service.FileService;
import kr.co.seok.service.GroupService;
import kr.co.seok.service.NotificationService;
import kr.co.seok.service.UrlService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class CronConfig {

    private List<MatterMostGroup> matterMostGroupLists;

    @Autowired
    private FileService fileService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UrlService urlService;

    @SneakyThrows
    @PostConstruct
    public void onStartup() {
        matterMostGroupLists = groupService.loadAll("");
        for(MatterMostGroup matterMostGroup : matterMostGroupLists){
            System.out.println(matterMostGroup);
        }
    }

    @SneakyThrows
    @Scheduled(fixedDelay = 20000)
    public void loadAllGroup() {
        matterMostGroupLists = groupService.loadAll("");
    }

    @SneakyThrows
    @Scheduled(fixedDelay = 60000)
    public void alertMessage() {
        for(MatterMostGroup matterMostGroup : matterMostGroupLists){
            System.out.println(matterMostGroup);
        }
    }

    public static void init(){
        System.out.println("first");
    }
}
