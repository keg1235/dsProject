package com.sara.project.pageSet.dto;

import com.sara.project.eletron.Eletron;
import com.sara.project.pageSet.PageSet;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageSetRequest {
    private Long id;
    private String company;
    private String graph ;
    private String dcpYn;

    private Integer graphMax ;

    private Integer graphMin ;

    private Integer graphDay ;

    private Integer graphTime ;

    private String dust;

    private Integer waitTime;

    public PageSet of(
    ) {

        return PageSet.builder()
                .id(1l)
                .company(this.company)
                .graph(this.graph)
                .dcpYn(this.dcpYn)
                .graphMax(this.graphMax)
                .graphMin(this.graphMin)
                .graphDay(this.graphDay)
                .graphTime(this.graphTime)
                .dust(this.dust)
                .waitTime(this.waitTime)
                .build();
    }
}
