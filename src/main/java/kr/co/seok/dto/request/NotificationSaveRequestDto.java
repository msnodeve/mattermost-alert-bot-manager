package kr.co.seok.dto.request;

import io.swagger.annotations.ApiModelProperty;
import kr.co.seok.dto.File;
import kr.co.seok.dto.MatterMostNotification;
import kr.co.seok.dto.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSaveRequestDto {
    private String message;
    private File file;

    @ApiModelProperty(hidden=true)
    private Member member;

    public MatterMostNotification toEntity(){
        return MatterMostNotification.builder()
                .message(message)
                .file(file)
                .member(member)
                .build();
    }
}

