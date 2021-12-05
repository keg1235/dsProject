package com.sara.project.ddc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sara.project.gateway.TbGateway;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="DDC", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"DDC", "DI", "GATEWAY_ID", "DCU"})})
public class Ddc {

    @Id
    private Long id;

    @Column
    private Integer ddc;

    @Column
    private Integer dcu;

    @Column
    private Integer seq;

    @Column
    private Integer di;

    @ManyToOne(targetEntity = TbGateway.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="GATEWAY_ID")
    private TbGateway gateway;


    @Column
    private String name;

    @Column
    private String use;

    @Column
    private String message;

    @Column
    private String color;

    @Column
    private String location;

    @Column
    private Integer diStatus;


    @Column
    private Integer doId;

    @Column
    private Integer doStatus;

    @Column
    private String diUse;

    @Column
    private String doUse;

}
