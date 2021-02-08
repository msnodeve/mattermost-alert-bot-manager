package kr.co.seok.repository;

import kr.co.seok.dto.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Image, Long> {
}
