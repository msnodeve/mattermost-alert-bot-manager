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
@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"url"}
        )
})
public class MatterMostUrl {
    @Id
    @Column(name = "url_id")
    @GeneratedValue
    private Long urlId;

    @Column(name = "url_alias", nullable = false, length = 128)
    private String urlAlias;

    @Column(name = "url", nullable = false, length = 254)
    private String url;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Builder
    public MatterMostUrl(String urlAlias, String url, Member member){
        this.urlAlias = urlAlias;
        this.url = url;
        this.member = member;
    }
}
