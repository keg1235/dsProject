package com.sara.project.gateway;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GatewayRepository  extends JpaRepository<TbGateway, Long>  {

    @Query(value = "SELECT IFNULL(MAX(id),0) FROM TB_GATEWAY", nativeQuery = true)
    Long findMaxValue();

    int countById(Long id);

    TbGateway findByIpAddress(String ipAddress);
}
