package kr.co.seok.dto;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MatterMostNotification {
    @Id
    @Column(name = "noti_id")
    @GeneratedValue
    private Long notiId;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Builder MatterMostNotification(String message, File file, Member member){
        this.message = message;
        this.file = file;
        this.member = member;
    }
}
