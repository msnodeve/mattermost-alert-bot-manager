package kr.co.seok.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class File {
    @Id
    @Column(name = "file_id")
    @GeneratedValue
    private Long fileId;

    @Column(name = "file_url", nullable = false, length = 512)
    private String fileUrl;

    @Column(name = "file_name", nullable = false, length = 512)
    private String fileName;

    @Builder
    public File(String fileUrl, String fileName){
        this.fileUrl = fileUrl;
        this.fileName = fileName;
    }
}
