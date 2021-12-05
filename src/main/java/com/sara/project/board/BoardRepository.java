package com.sara.project.board;

import com.sara.project.ddc.Ddc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "SELECT IFNULL(MAX(id),0) FROM BOARD", nativeQuery = true)
    Long findMaxValue();
}
