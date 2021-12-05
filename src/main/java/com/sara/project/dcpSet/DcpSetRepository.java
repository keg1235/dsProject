package com.sara.project.dcpSet;

import com.sara.project.dcp.Dcp;
import com.sara.project.ddc.CommonCode;
import com.sara.project.ddc.Ddc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DcpSetRepository  extends JpaRepository<DcpSet, Long> {


    @Query(value = "SELECT IFNULL(MAX(id),0) FROM DCP_SET", nativeQuery = true)
    Long findMaxValue();


    @Query(value =
            "SELECT "+
                    "DCP AS id, " +
                    "DCP AS Name " +
                    "FROM DCP_SET " +
                    "WHERE 1=1 " +
                    "GROUP BY DCP"
            , nativeQuery = true)
    List<CommonCode> getGroupDcp();

    List<DcpSet> findByDcp(int ddcId);
}
