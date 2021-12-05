package com.sara.project.scdata;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="SC_DATA")
public class ScData {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String memo;

    @Column
    private LocalDate endDate;

    @Column
    private LocalDate startDate;

    @Column
    private String weeks;

    @Column
    private String hour;

    @Column
    private String min;

    @Column
    private String onOff;

    @Column
    private String useYn;

    @Column
    private Integer waitTime;


}
