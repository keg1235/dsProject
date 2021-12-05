package com.sara.project.dcpReport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sara.project.dcpSet.DcpSet;
import com.sara.project.gateway.TbGateway;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="DCP_REPORT")
public class DcpReport {

    // DCPSET을 자유롭게 삭제 하기위해 연관관계 없이 바로 저장
    @Id
    private Long id;

    @CreatedDate
    private LocalDateTime inDate;

    @Column
    private String inValue;

    @Column
    private String inType;

    @Column
    private String ipAddress;

    @Column
    private String name;

    @Column
    private Integer dcpNum;


}
