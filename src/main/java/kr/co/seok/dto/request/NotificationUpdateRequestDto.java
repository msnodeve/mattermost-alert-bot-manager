package kr.co.seok.dto.request;

import kr.co.seok.dto.File;
import kr.co.seok.dto.MatterMostNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationUpdateRequestDto {
    private String message;
    private File file;

    public MatterMostNotification toEntityNoFile(){
        return MatterMostNotification.builder()
                .message(message)
                .build();
    }
    public MatterMostNotification toEntity(){
        return MatterMostNotification.builder()
                .message(message)
                .file(file)
                .build();
    }
}

