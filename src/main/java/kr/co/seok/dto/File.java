package kr.co.seok.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class File {
    @Id
    @Column(name = "file_id")
    @GeneratedValue
    private Long fileId;

    @ApiModelProperty(hidden=true)
    @Column(name = "file_url", nullable = false, length = 512)
    private String fileUrl;

    @ApiModelProperty(hidden=true)
    @Column(name = "file_name", nullable = false, length = 512)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Builder
    public File(String fileUrl, String fileName, Member member){
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.member = member;
    }
}
