package kr.co.seok.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MatterMostGroup{
    @Id
    @Column(name = "group_id")
    @GeneratedValue
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "url_id")
    private MatterMostUrl matterMostUrl;

    @ManyToOne
    @JoinColumn(name = "noti_id")
    private MatterMostNotification matterMostNotification;
}