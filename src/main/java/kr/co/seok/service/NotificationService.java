package kr.co.seok.service;

import kr.co.seok.dto.MatterMostNotification;
import kr.co.seok.dto.request.NotificationSaveRequestDto;
import kr.co.seok.dto.request.NotificationUpdateRequestDto;
import kr.co.seok.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public MatterMostNotification save(NotificationSaveRequestDto notificationSaveRequestDto) {
        return notificationRepository.save(notificationSaveRequestDto.toEntityNoFile());
    }

    public List<MatterMostNotification> loadAll(String message) throws Exception {
        return notificationRepository.findByMessageContains(message);
    }

    public MatterMostNotification update(Long id, NotificationUpdateRequestDto notificationUpdateRequestDto) throws Exception {
        MatterMostNotification matterMostNotification = notificationRepository.findById(id).orElseThrow(() -> new Exception("해당되는 Noti 가 없습니다."));
        matterMostNotification.setFile(notificationUpdateRequestDto.getFile());
        matterMostNotification.setMessage(notificationUpdateRequestDto.getMessage());
        return notificationRepository.save(matterMostNotification);
    }
}
