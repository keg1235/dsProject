package com.sara.project.eletron;

import com.sara.project.eletron.dto.EletronExcelMapper;
import com.sara.project.eletron.dto.EletronMapper;
import com.sara.project.gateway.TbGateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EletronRepository extends JpaRepository<Eletron, Long>{
@Query(value = "SELECT IFNULL(MAX(id),0) FROM ELETRON", nativeQuery = true)
    Long findMaxValue();

    int countById(Long id);

    Eletron findByIpAddress(String ipAddress);

    List<Eletron> findByDcp(Long id);


    @Query(value = "SELECT *  FROM ELETRON ORDER BY IN_DATE", nativeQuery = true)
    List<Eletron> findmonthDay();

    @Query(value = "SELECT  *, "+
            " (SELECT TO_CHAR(MIN(IN_DATE) , 'YYYY-MM-DD HH24:MI') FROM ELETRON WHERE TO_CHAR(IN_DATE , 'YYYY-MM-DD') = A.IN_DATE AND IN_VALUE = MAX_VALUE ) as MAX_DATE, " +
            " (SELECT TO_CHAR(MIN(IN_DATE) , 'YYYY-MM-DD HH24:MI') FROM ELETRON WHERE TO_CHAR(IN_DATE , 'YYYY-MM-DD') = A.IN_DATE AND IN_VALUE = MIN_VALUE ) as MIN_DATE " +
            " FROM ( " +
            " SELECT  TO_CHAR(IN_DATE , 'YYYY-MM-DD') IN_DATE ,MAX(NOW_TOTAL) NOW_TOTAL ,MAX(IN_VALUE)  MAX_VALUE ,MIN(IN_VALUE) MIN_VALUE  " +
            "            FROM ELETRON A  " +
            "            WHERE IN_DATE  BETWEEN SYSDATE-15 AND SYSDATE  " +
            "            AND NOW_TOTAL IS NOT NULL  " +
            "           GROUP BY  TO_CHAR(IN_DATE , 'YYYY-MM-DD')  " +
            ") A  ", nativeQuery = true)
    List<EletronMapper> findGrap(String ipAddress);


    @Query(value = "SELECT  TO_CHAR(IN_DATE , 'YYYY-MM-DD HH24:MI:SS') IN_DATE ,NOW_TOTAL ,IN_VALUE, IP_ADDRESS " +
            " FROM ELETRON " +
            " WHERE 1=1 "+
            " AND NOW_TOTAL IS NOT NULL " +
            " AND IP_ADDRESS = ? " +
            " AND TO_CHAR(IN_DATE,'YYYY-MM-DD') BETWEEN ? AND ? " +
            " ORDER BY IN_DATE DESC", nativeQuery = true)
    List<EletronExcelMapper> findExcelData(String ipAddress,String std ,String end);

    @Query(value = "SELECT  *, "+
            " (SELECT TO_CHAR(MIN(IN_DATE) , 'YYYY-MM-DD HH24:MI') FROM ELETRON WHERE TO_CHAR(IN_DATE , 'YYYY-MM-DD') = A.IN_DATE AND IN_VALUE = MAX_VALUE ) as MAX_DATE, " +
            " (SELECT TO_CHAR(MIN(IN_DATE) , 'YYYY-MM-DD HH24:MI') FROM ELETRON WHERE TO_CHAR(IN_DATE , 'YYYY-MM-DD') = A.IN_DATE AND IN_VALUE = MIN_VALUE ) as MIN_DATE " +
            " FROM ( " +
            " SELECT  TO_CHAR(IN_DATE , 'YYYY-MM-DD') IN_DATE ,MAX(NOW_TOTAL) NOW_TOTAL ,MAX(IN_VALUE)  MAX_VALUE ,MIN(IN_VALUE) MIN_VALUE  " +
            "            FROM ELETRON A  " +
            "            WHERE 1=1 " +
            "            AND NOW_TOTAL IS NOT NULL  " +
            "           GROUP BY  TO_CHAR(IN_DATE , 'YYYY-MM-DD')  " +
            ") A  ", nativeQuery = true)
    List<EletronMapper> findGrapExcel(String ipAddress);
}
