package kr.co.seok.repository;

import kr.co.seok.dto.MatterMostNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<MatterMostNotification, Long> {
    List<MatterMostNotification> findByMessageContains(String message) throws Exception;
}
