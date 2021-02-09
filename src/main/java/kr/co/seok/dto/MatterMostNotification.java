package kr.co.seok.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

}
