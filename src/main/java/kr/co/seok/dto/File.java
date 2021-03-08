package kr.co.seok.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    @Builder
    public File(String fileUrl, String fileName){
        this.fileUrl = fileUrl;
        this.fileName = fileName;
    }
}
