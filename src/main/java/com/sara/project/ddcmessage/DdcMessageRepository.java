package com.sara.project.ddcmessage;

import com.sara.project.ddcmessage.ddcMessageDto.DdcUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DdcMessageRepository  extends JpaRepository<DdcMessage, Long> {

    @Query(value = "SELECT C.DDC AS Ddc ,C.DI AS Di , B.ID AS Id, A.EMAIL AS Email, PONE_NUM  AS Phone, A.NAME AS Name FROM USER A INNER JOIN DDC_MESSAGE B ON A.ID = B.USER_ID  INNER JOIN DDC C ON B.DDC_ID = C.ID   WHERE A.ID_USE_YN = 'YES' AND C.ID = ?", nativeQuery = true)
    List<DdcUser> findDi(Integer id);

    @Query(value = "SELECT IFNULL(MAX(id),0) FROM DDC_MESSAGE", nativeQuery = true)
    Long findMaxValue();

    void deleteByDdcId(Long ddcId);
}
