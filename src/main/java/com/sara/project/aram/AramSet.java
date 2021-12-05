package com.sara.project.aram;

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
@Table(name="ARAM_SET")
public class AramSet {
    @Id
    private Long id;

    @Column
    private Long aramMinOne;

    @Column
    private Long aramMaxOne;

    @Column
    private Boolean aramUseOne;

    @Column
    private Long aramMinTwo;

    @Column
    private Long aramMaxTwo;

    @Column
    private Boolean aramUseTwo;

    @Column
    private Long aramMinThree;

    @Column
    private Long aramMaxThree;

    @Column
    private Boolean aramUseThree;
}
