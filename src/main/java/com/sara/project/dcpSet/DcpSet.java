package com.sara.project.dcpSet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sara.project.gateway.TbGateway;
import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="DCP_SET")
public class DcpSet {

    @Id
    private Long id;

    @Column
    private String location;

    @Column
    private String name;

    @Column
    private String memo;

    @Column
    private Integer dcp; //기기 번호


    @ManyToOne(targetEntity = TbGateway.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="GATEWAY_ID")
    private TbGateway gateway;

    @Column
    private Integer num; //기기 위치

    @Column
    private Integer address; //메모리 주소

    @Column
    private String type; //디지털 (온도) ,노말(di ,do)

    @Column
    private String readWriteType; // write 인지

    @Column
    private String dataType; // double , Long , word 3가지

    @Column
    private String volumeMin;

    @Column
    private String volumeMax;

    @Column
    private String displayMin;

    @Column
    private String displayMax;

    @Column
    private String unit; // 부호

    @Column
    private String value; //값

    @Column
    private Integer indexNum;

    @Column
    private String dcpSend;

}
