package com.sara.project.drawData;

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
@Table(name="DRAW_GROUP")
public class DrawGroup {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String useYn;


}
