package com.sara.project.dcu;

import com.sara.project.ddc.CommonCode;
import com.sara.project.ddc.Ddc;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DcuRepository {

    @Query(value = "SELECT IFNULL(MAX(id),0) FROM DCU", nativeQuery = true)
    Long findMaxValue();

    int countById(Long id);

    List<Dcu> findByDdc(Integer id);

    @Query(value =
            "SELECT "+
                    "DCU AS id, " +
                    "DCU AS Name " +
                    "FROM DCU " +
                    "GROUP BY DCU"
            , nativeQuery = true)
    List<CommonCode> getGroupDcu();

    Ddc findByDcuAndDoId(int i, int i1);

    Ddc findByDcuAndDi(Integer id, int i);
}
