package com.sara.project.drawData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DrawGroupRepository extends JpaRepository<DrawGroup, Long> {

    @Query(value = "SELECT IFNULL(MAX(id),0)+1 FROM DRAW_GROUP", nativeQuery = true)
    Long findMaxValue();
}
