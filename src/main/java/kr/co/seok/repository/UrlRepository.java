package kr.co.seok.repository;

import kr.co.seok.dto.MatterMostUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlRepository extends JpaRepository<MatterMostUrl, Long> {
    List<MatterMostUrl> findByUrlAliasContains(String urlName) throws Exception;
}
