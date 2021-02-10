package kr.co.seok.dto.request;

import kr.co.seok.dto.MatterMostUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UrlSaveRequestDto {
    private String urlAlias;
    private String url;

    public MatterMostUrl toEntity(){
        return MatterMostUrl.builder()
                .urlAlias(urlAlias)
                .url(url)
                .build();
    }
}
