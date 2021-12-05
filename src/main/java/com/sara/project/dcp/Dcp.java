package com.sara.project.dcp;

import com.sara.project.ddc.Ddc;
import com.sara.project.gateway.GatewayRepository;
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
@Table(name="DCP")
public class Dcp{
    @Id
    private Long id;

    @Column
    private String location;

    @Column
    private String name;

    @Column
    private String memo;

    @ManyToOne(targetEntity = TbGateway.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="GATEWAY_ID")
    private TbGateway gateway;

    @Column
    private Integer delayTimer;

    @Column
    private String useYn;

    @Column
    private String type;

    @Column
    private Integer ddc;

    @Column
    private Integer onSuggest;

    @Column
    private Integer offSuggest;

    @Column
    private String onType;

    @Column
    private String offType;

    @Column
    private Integer saveValue;

    @Column
    private String mainYn;


}
