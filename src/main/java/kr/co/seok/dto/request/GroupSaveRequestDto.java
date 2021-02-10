package kr.co.seok.dto.request;

import kr.co.seok.dto.MatterMostGroup;
import kr.co.seok.dto.MatterMostNotification;
import kr.co.seok.dto.MatterMostUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupSaveRequestDto {
    private MatterMostUrl matterMostUrl;
    private MatterMostNotification matterMostNotification;

    public MatterMostGroup toEntity(){
        return MatterMostGroup.builder()
                .matterMostUrl(matterMostUrl)
                .matterMostNotification(matterMostNotification)
                .build();
    }
}
