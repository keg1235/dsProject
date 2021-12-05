package com.sara.project.dcpReport;

import com.sara.project.eletron.Eletron;
import com.sara.project.eletron.dto.EletronExcelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DcpReportRepository extends JpaRepository<DcpReport, Long> {

    @Query(value = "SELECT IFNULL(MAX(id),0) FROM DCP_REPORT", nativeQuery = true)
    Long findMaxValue();

    @Query(value = "SELECT  * " +
            " FROM DCP_REPORT " +
            " WHERE 1=1 "+
            " AND TO_CHAR(IN_DATE,'YYYY-MM-DD') BETWEEN ? AND ? " +
            " ORDER BY IN_DATE DESC", nativeQuery = true)
    List<DcpReport> findExcelData(String std , String end);
}
