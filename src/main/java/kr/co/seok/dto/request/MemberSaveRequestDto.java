package kr.co.seok.dto.request;

import kr.co.seok.dto.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequestDto {
    private String id;
    private String password;

    public Member toEntity(){
        return Member.builder()
                .id(id)
                .password(password)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}

