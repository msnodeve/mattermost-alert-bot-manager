package kr.co.seok.dto.request;

import kr.co.seok.dto.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileSaveRequestDto {
    private String imageUrl;

    public Image toEntity(){
        return Image.builder()
                .imgUrl(imageUrl)
                .build();
    }
}
