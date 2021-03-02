package kr.co.seok.dto;

import lombok.*;

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

    @Builder
    public MatterMostNotification(String message){
        this.message = message;
    }

    @Builder MatterMostNotification(String message, File file){
        this.message = message;
        this.file = file;
    }
}
