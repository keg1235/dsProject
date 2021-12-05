package com.sara.project.pageSet;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="PAGE_SET")
public class PageSet {
    @Id
    private Long id;

    @Column
    private String company;

    @Column
    private String graph ;

    @Column
    private String dcpYn;

    @Column
    private Integer graphMax ;

    @Column
    private Integer graphMin ;

    @Column
    private Integer graphDay ;

    @Column
    private Integer graphTime ;

    @Column
    private String dust;

    @Column
    private Integer waitTime;

}
