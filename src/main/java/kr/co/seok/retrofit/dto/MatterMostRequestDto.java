package kr.co.seok.retrofit.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class MatterMostRequestDto {

    private Attachments[] attachments;

    public MatterMostRequestDto() {
        attachments = new Attachments[1];
        attachments[0] = new Attachments();
    }

}
