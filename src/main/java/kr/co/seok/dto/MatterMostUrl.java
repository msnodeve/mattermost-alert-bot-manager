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
public class MatterMostUrl {
    @Id
    @Column(name = "url_id")
    @GeneratedValue
    private Long urlId;

    @Column(name = "url_alias", nullable = false, length = 128)
    private String urlAlias;

    @Column(name = "url", nullable = false, length = 512)
    private String url;

    @Builder
    public MatterMostUrl(String urlAlias, String url){
        this.urlAlias = urlAlias;
        this.url = url;
    }
}
