package com.sara.project.company;

import com.sara.project.ddc.Ddc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query(value = "SELECT IFNULL(MAX(id),0) FROM COMPANY", nativeQuery = true)
    Long findMaxValue();
}
