package com.sara.project.pageSet.dto;

import com.sara.project.eletron.Eletron;
import com.sara.project.eletron.dto.EletronResponseDto;
import com.sara.project.pageSet.PageSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.ZoneOffset;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageSetResponse {

    private Long id;
    private String company;
    private String graph ;
    private String dcpYn;
    private Integer graphMax ;
    private Integer graphMin ;
    private Integer graphDay ;
    private Integer graphTime;
    private String dust;
    private Integer waitTime;

    public static PageSetResponse of(
            PageSet pageSet
    ) {

        return PageSetResponse.builder()
                .id(1l)
                .company(pageSet.getCompany())
                .graph(pageSet.getGraph())
                .dcpYn(pageSet.getDcpYn())
                .graphDay(pageSet.getGraphDay())
                .graphMax(pageSet.getGraphMax())
                .graphMin(pageSet.getGraphMin())
                .graphTime(pageSet.getGraphTime())
                .dust(pageSet.getDust())
                .waitTime(pageSet.getWaitTime())
                .build();
    }
}
