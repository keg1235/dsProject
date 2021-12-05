package com.sara.project.dcp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DcpRepository extends JpaRepository<Dcp, Long> {
    int countById(Long id);


    @Query(value = "SELECT IFNULL(MAX(id),0) FROM DCP", nativeQuery = true)
    Long findMaxValue();

    Dcp findByMainYn(String yes);
}
