package com.sara.project.message;

import com.sara.project.ddc.Ddc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value = "SELECT IFNULL(MAX(id),0) FROM MESSAGE", nativeQuery = true)
    Long findMaxValue();
}
