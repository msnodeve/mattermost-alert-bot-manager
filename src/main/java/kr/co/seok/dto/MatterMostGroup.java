package kr.co.seok.dto;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MatterMostGroup{
    @Id
    @Column(name = "group_id")
    @GeneratedValue
    private Long groupId;

    @Column(name = "group_noti_time")
    private String time;

    @ManyToOne
    @JoinColumn(name = "url_id")
    private MatterMostUrl matterMostUrl;

    @ManyToOne
    @JoinColumn(name = "noti_id")
    private MatterMostNotification matterMostNotification;

    @Builder
    public MatterMostGroup(MatterMostUrl matterMostUrl, MatterMostNotification matterMostNotification, String time){
        this.matterMostUrl = matterMostUrl;
        this.matterMostNotification = matterMostNotification;
        this.time = time;
    }
}