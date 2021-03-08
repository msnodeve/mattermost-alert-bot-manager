package kr.co.seok.dto.request;

import io.swagger.annotations.ApiModelProperty;
import kr.co.seok.dto.File;
import kr.co.seok.dto.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileSaveRequestDto {
    private String fileUrl;
    private String fileName;

    @ApiModelProperty(hidden=true)
    private Member member;

    public File toEntity(){
        return File.builder()
                .fileUrl(fileUrl)
                .fileName(fileName)
                .member(member)
                .build();
    }
}
