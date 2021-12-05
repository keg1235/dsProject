package com.sara.project.company;

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
@Table(name="COMPANY")
public class Company {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String addr;

    @Column
    private String tel;

    @Column
    private String useYn;


}
