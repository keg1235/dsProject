package com.sara.project.qna;

import com.sara.project.ddc.Ddc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    @Query(value = "SELECT IFNULL(MAX(id),0) FROM QNA", nativeQuery = true)
    Long findMaxValue();
}
