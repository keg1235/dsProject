package com.sara.project.drawData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShadowRepository extends JpaRepository<Shadow, Long> {

    @Query(value = "SELECT IFNULL(MAX(id),0)+1 FROM SHADOW", nativeQuery = true)
    Long findMaxValue();

    void deleteByGroupId(Long id);
}
