package com.sara.project.drawData;

import com.sara.project.dcp.Dcp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DrawDataRepository extends JpaRepository<DrawData, Long> {

    List<DrawData> findByGroupId(Long id);

    @Query(value = "SELECT IFNULL(MAX(id),0)+1 FROM DRAW_DATA", nativeQuery = true)
    Long findMaxValue();

    List<DrawData> findByGroupIdAndGroupType(Long id, String background);

    void deleteByGroupId(Long id);
}
