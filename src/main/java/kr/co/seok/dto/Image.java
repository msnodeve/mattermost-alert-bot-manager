package kr.co.seok.dto;

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
public class Image {
    @Id
    @Column(name = "img_id")
    @GeneratedValue
    private Long imgId;

    @Column(name = "img_url", nullable = false, length = 512)
    private String imgUrl;
}
