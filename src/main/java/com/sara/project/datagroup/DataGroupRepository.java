package com.sara.project.datagroup;

import com.sara.project.datagroup.dto.DataGroupMapper;
import com.sara.project.datagroup.dto.DataGroupResponseDto;
import com.sara.project.ddc.Ddc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataGroupRepository extends JpaRepository<DataGroup, Long> {
    void deleteByGroupId(Long groupId);

    @Query(value = "SELECT IFNULL(MAX(id),0) FROM DATA_GROUP", nativeQuery = true)
    Long findMaxValue();

    @Query(value = "SELECT IFNULL(MAX(group_id),0) FROM DATA_GROUP", nativeQuery = true)
    Long findMaxGroupValue();

    @Query(value = "SELECT GROUP_ID as groupid,NAME,SORT,DCP_ID as dcpid,SC_DATA_ID as scdataid FROM DATA_GROUP GROUP BY GROUP_ID,NAME,SORT,DCP_ID,SC_DATA_ID ORDER BY SORT", nativeQuery = true)
    List<DataGroupMapper> findgroup();

    List<DataGroupResponseDto> findByGroupId(Long id);

    List<DataGroup> findByDcpId(Long id);


    @Query(value = "SELECT * FROM DATA_GROUP WHERE SC_DATA_ID = ? ", nativeQuery = true)
    List<DataGroup> findByScDateId(Long id);
}
