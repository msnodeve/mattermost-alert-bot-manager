package kr.co.seok.dto.request;

import kr.co.seok.dto.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileSaveRequestDto {
    private String fileUrl;
    private String fileName;

    public File toEntity(){
        return File.builder()
                .fileUrl(fileUrl)
                .fileName(fileName)
                .build();
    }
}
