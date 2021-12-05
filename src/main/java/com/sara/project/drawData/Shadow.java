package com.sara.project.drawData;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Table(name="SHADOW")
public class Shadow {

    @Id
    private Long id;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean affectStroke;

    @Column(columnDefinition="Decimal(12,3) default '0.000'")
    private BigDecimal  blur;

    @Column
    private String color;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean nonScaling;

    @Column(columnDefinition="Decimal(12,3) default '0.000'")
    private BigDecimal offsetX;


    @Column
    private Long groupId;
}
