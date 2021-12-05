package com.sara.project.aram;

import com.sara.project.eletron.Eletron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AramRepository extends JpaRepository<Aram, Long> {
    List<Aram> findByConfirmYn(String n);


    @Query(value = "SELECT IFNULL(MAX(id),0) FROM ARAM", nativeQuery = true)
    Long findMaxValue();


    @Modifying
    @Query(value = "UPDATE ARAM SET CONFIRM_YN='Y'", nativeQuery = true)
    void allUpdate();
}
