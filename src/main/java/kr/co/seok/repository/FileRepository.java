package kr.co.seok.repository;

import kr.co.seok.dto.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByFileNameContains(String fileName) throws Exception;
}
