package kr.co.seok.retrofit.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Attachments {
    private String fallback;
    private String pretext;
    private String color;
    private String text;
    private String author_name;
    private String author_icon;
    private String title;
    private String image_url;
}
