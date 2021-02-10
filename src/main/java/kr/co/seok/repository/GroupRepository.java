package kr.co.seok.repository;

import kr.co.seok.dto.MatterMostGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<MatterMostGroup, Long> {
    List<MatterMostGroup> findByTimeContains(String time) throws Exception;
}
