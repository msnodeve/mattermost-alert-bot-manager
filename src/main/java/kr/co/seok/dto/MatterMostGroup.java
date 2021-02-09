package kr.co.seok.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MatterMostGroup{
    @Id
    @Column(name = "group_id")
    @GeneratedValue
    private Long groupId;

    @Column(name = "group_noti_time")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "url_id")
    private MatterMostUrl matterMostUrl;

    @ManyToOne
    @JoinColumn(name = "noti_id")
    private MatterMostNotification matterMostNotification;
}