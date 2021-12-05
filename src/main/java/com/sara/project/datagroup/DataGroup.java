package com.sara.project.datagroup;

import com.sara.project.dcp.Dcp;
import com.sara.project.ddc.Ddc;
import com.sara.project.scdata.ScData;
import lombok.*;

import javax.persistence.*;


@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="DATA_GROUP")
public class DataGroup {

    @Id
    private Long id;

    private Long groupId;

    private String name;

    @ManyToOne(targetEntity = Ddc.class, fetch = FetchType.LAZY)
    @JoinColumn(name="DDC_ID")
    private Ddc ddcId;

    @ManyToOne(targetEntity = ScData.class, fetch = FetchType.LAZY)
    @JoinColumn(name="SC_DATA_ID")
    private ScData scData;

    @ManyToOne(targetEntity = Dcp.class, fetch = FetchType.LAZY)
    @JoinColumn(name="DCP_ID")
    private Dcp dcp;

    private Integer sort;

}
