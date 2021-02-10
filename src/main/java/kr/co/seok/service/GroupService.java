package kr.co.seok.service;

import kr.co.seok.dto.MatterMostGroup;
import kr.co.seok.dto.MatterMostNotification;
import kr.co.seok.dto.MatterMostUrl;
import kr.co.seok.repository.GroupRepository;
import kr.co.seok.repository.NotificationRepository;
import kr.co.seok.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private GroupRepository groupRepository;

    public MatterMostGroup save(Long urlId, Long notiId, String time) throws Exception {
        MatterMostUrl matterMostUrl = urlRepository.findById(urlId).orElseThrow(() -> new Exception("등록된 URL 이 없습니다."));
        MatterMostNotification matterMostNotification = notificationRepository.findById(notiId).orElseThrow(() -> new Exception("등록된 Notification 이 없습니다."));

        MatterMostGroup matterMostGroup = MatterMostGroup.builder()
                .matterMostUrl(matterMostUrl)
                .matterMostNotification(matterMostNotification)
                .time(time)
                .build();

        return groupRepository.save(matterMostGroup);
    }

    public List<MatterMostGroup> loadAll(String time) throws Exception {
        return groupRepository.findByTimeContains(time);
    }

    public MatterMostGroup update(Long groupId, Long urlId, Long notiId, String time) throws Exception{
        MatterMostGroup matterMostGroup = groupRepository.findById(groupId).orElseThrow(()-> new Exception("해당되는 Group 이 없습니다."));
        MatterMostUrl matterMostUrl = urlRepository.findById(urlId).orElseThrow(() -> new Exception("등록된 URL 이 없습니다."));
        MatterMostNotification matterMostNotification = notificationRepository.findById(notiId).orElseThrow(() -> new Exception("등록된 Notification 이 없습니다."));
        matterMostGroup.setMatterMostNotification(matterMostNotification);
        matterMostGroup.setMatterMostUrl(matterMostUrl);
        matterMostGroup.setTime(time);
        return groupRepository.save(matterMostGroup);
    }
}
