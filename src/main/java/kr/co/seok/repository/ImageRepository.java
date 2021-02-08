package kr.co.seok.repository;

import kr.co.seok.dto.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
