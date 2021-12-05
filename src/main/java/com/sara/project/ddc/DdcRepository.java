package com.sara.project.ddc;

import com.sara.project.common.util.CommonGroupCode;
import com.sara.project.ddc.dto.DdcGroupMapper;
import com.sara.project.ddc.dto.DdcMapper;
import com.sara.project.gateway.TbGateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Column;
import java.util.Collection;
import java.util.List;

public interface DdcRepository  extends JpaRepository<Ddc, Long> {

    @Query(value = "SELECT IFNULL(MAX(id),0) FROM DDC", nativeQuery = true)
    Long findMaxValue();

    int countById(Long id);

    List<Ddc> findByDdc(Integer id);

    @Query(value =
            "SELECT "+
                    "DDC AS id, " +
                    "DDC AS Name " +
                    "FROM DDC " +
                    "WHERE DCU IS NULL " +
                    "GROUP BY DDC"
            , nativeQuery = true)
    List<CommonCode> getGroupDdc();

    @Query(value =
            "SELECT "+
                    "DDC +'-'+DCU AS id, " +
                    "DDC +'-'+DCU AS Name " +
                    "FROM DDC " +
                    "WHERE DCU IS NOT NULL " +
                    "GROUP BY DDC"
            , nativeQuery = true)
    List<CommonCode> getGroupDcu();

    Ddc findByDdcAndDoId(int i, int i1);

    Ddc findByDdcAndDi(Integer id, Integer i);

    Ddc findByDdcAndDiAndDcu(Integer id, Integer i,Integer dcu);

    Ddc findByDdcAndDiAndDcuAndGateway(Integer id, Integer i, Integer dcu, Long tbGateway);

    Ddc findByDdcAndDoIdAndDcuAndGateway(Integer id, Integer i,Integer dcu,Long tbGateway);

    @Query(value =
            "SELECT "+
                    "DDC, " +
                    "DCU, " +
                    "ip_address as ipAddress, "+
                    "port " +
                    "FROM DDC A " +
                    "JOIN TB_GATEWAY B ON A.GATEWAY_ID = B.ID " +
                    "WHERE DCU IS NOT NULL " +
                    "AND DO_USE = 'YES'  " +
                    "GROUP BY DDC, DCU, ip_address, port"
            , nativeQuery = true)
    List<DdcMapper> getGroupMDdcDo();

    @Query(value =
            "SELECT "+
                    "DDC, " +
                    "DCU, " +
                    "ip_address as ipAddress, "+
                    "port " +
                    "FROM DDC A " +
                    "JOIN TB_GATEWAY B ON A.GATEWAY_ID = B.ID " +
                    "WHERE DCU IS NOT NULL " +
                    "AND DI_USE = 'YES'  " +
                    "GROUP BY DDC, DCU, ip_address, port"
            , nativeQuery = true)
    List<DdcMapper> getGroupMDdcDi();

    @Query(value =
            "SELECT "+
                    "DDC, " +
                    "ip_address as ipAddress, "+
                    "port " +
                    "FROM DDC A " +
                    "JOIN TB_GATEWAY B ON A.GATEWAY_ID = B.ID " +
                    "WHERE DCU IS NULL " +
                    "AND A.id  =  ? " +
                    "GROUP BY DDC, ip_address, port"
            , nativeQuery = true)
    DdcMapper getGroupMapperDdc( Long id);

    @Query(value =
            "SELECT "+
                    "DDC, " +
                    "DCU, " +
                    "ip_address as ipAddress, "+
                    "port " +
                    "FROM DDC A " +
                    "JOIN TB_GATEWAY B ON A.GATEWAY_ID = B.ID " +
                    "WHERE DCU IS NOT NULL " +
                    "AND A.id  =  ? " +
                    "GROUP BY DDC,DCU, ip_address, port"
            , nativeQuery = true)
    DdcMapper getGroupMapperDcu( Long id);


    @Query(value =
            "SELECT "+
                    "DDC, " +
                    "ip_address as ipAddress, "+
                    "port " +
                    "FROM DDC A " +
                    "JOIN TB_GATEWAY B ON A.GATEWAY_ID = B.ID " +
                    "WHERE DCU IS NULL " +
                    "AND DI_USE = 'YES'  " +
                    "GROUP BY DDC, ip_address, port"
            , nativeQuery = true)
    List<DdcGroupMapper> getDdcDiGroup();

    @Query(value =
            "SELECT "+
                    "DDC, " +
                    "ip_address as ipAddress, "+
                    "port " +
                    "FROM DDC A " +
                    "JOIN TB_GATEWAY B ON A.GATEWAY_ID = B.ID " +
                    "WHERE DCU IS NULL " +
                    "AND DO_USE = 'YES'  " +
                    "GROUP BY DDC, ip_address, port"
            , nativeQuery = true)
    List<DdcGroupMapper> getDdcDoGroup();

    @Query(value =
            "SELECT "+
                    "DDC, " +
                    "DCU, " +
                    "ip_address as ipAddress, "+
                    "port " +
                    "FROM DDC A " +
                    "JOIN TB_GATEWAY B ON A.GATEWAY_ID = B.ID " +
                    "WHERE A.id IN (:idList)" +
                    "GROUP BY DDC, DCU, ip_address, port"
            , nativeQuery = true)
    List<DdcMapper> getDdcGroupList(@Param("idList") List<Long> idList);

    Ddc findByDdcAndDoIdAndDcu(Integer ddc, Integer count, Integer num);

    List<Ddc> findByDdcAndDcu(Integer ddc, Integer dcu);

    List<Ddc> findByDdcAndGatewayAndDcuIsNotNull(Integer ddc, Long id);

    Ddc findByDdcAndDiAndDcuAndGateway_Id(Integer ddc, int i, Object o, Long id);

    List<Ddc> findByDdcAndGateway_IdAndDcuIsNotNull(Integer ddc, Long id);

    Ddc findByDdcAndDoIdAndDcuAndGateway_Id(Integer ddc, int i, Object o, Long id);

    List<Ddc> findByDcuIsNull();

    List<Ddc> findByDcuIsNotNull();
}
