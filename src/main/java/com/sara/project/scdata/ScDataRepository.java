package com.sara.project.scdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScDataRepository extends JpaRepository<ScData, Long> {

    @Query(value = "SELECT IFNULL(MAX(id),0) FROM SC_DATA", nativeQuery = true)
    Long findMaxValue();
}
