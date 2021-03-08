package kr.co.seok.retrofit.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MatterMostRequestDto {
    private String fallback;
    private String color;
    private String pretext;
    private String text;

    // 저자 세부정보
    @SerializedName("author_name")
    private String authorName;
    @SerializedName("author_link")
    private String authorLink;
    @SerializedName("author_icon")
    private String authorIcon;

    // 제목
    private String title;
    @SerializedName("title_link")
    private String titleLink;

    // 이미지
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("thumb_url")
    private String thumbUrl;

    // 푸터
    private String footer;
    @SerializedName("footer_icon")
    private String footerIcon;
}
