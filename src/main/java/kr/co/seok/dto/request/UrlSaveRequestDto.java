package kr.co.seok.dto.request;

import io.swagger.annotations.ApiModelProperty;
import kr.co.seok.dto.MatterMostUrl;
import kr.co.seok.dto.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlSaveRequestDto {
    private String urlAlias;
    private String url;

    @ApiModelProperty(hidden=true)
    private Member member;

    public MatterMostUrl toEntity(){
        return MatterMostUrl.builder()
                .urlAlias(urlAlias)
                .url(url)
                .member(member)
                .build();
    }
}
