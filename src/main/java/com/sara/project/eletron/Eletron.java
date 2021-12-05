package com.sara.project.eletron;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="ELETRON")
public class Eletron {

    @Id
    private Long id;

    @Column
    private String ipAddress;

    @Column
    private Long dcp;

    @CreatedDate
    private LocalDateTime inDate;

    @Column
    private Integer inValue;

    @Column
    private String inType;

    @Column
    private BigDecimal nowTotal;



}
