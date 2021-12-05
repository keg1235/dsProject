package com.sara.project.scdata.dto;

import com.sara.project.scdata.ScData;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScDataRequestDto {

    private Long id;

    private String name;

    private String memo;

    private LocalDate endDate;

    private LocalDate startDate;

    private String weeks;

    private String time;

    private String onOff;

    private String useYn;

    private Integer waitTime;

    private boolean sunday;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;



    public ScData of(Long id) {


        LocalDate startDate =
                StringUtils.isEmpty(this.startDate) ? null : LocalDate.parse(this.startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        LocalDate endDate =
                StringUtils.isEmpty(this.endDate) ? null :LocalDate.parse(this.endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        String dayOfWeek = "";

        String[] hour = this.time.split(":");


        return ScData.builder()
                .id(id)
                .name(this.name)
                .memo(this.memo)
                .waitTime(this.waitTime)
                .weeks(getDayOfWeek())
                .startDate(startDate)
                .endDate(endDate)
                .useYn(this.useYn)
                .hour(hour[0])
                .min(hour[1])
                .onOff(this.onOff)
                .build();

    }


    private String getDayOfWeek(){
        String dayOfWeek = "";
        dayOfWeek += this.sunday == true ? "1" : "0";
        dayOfWeek += this.monday == true ? "1" : "0";
        dayOfWeek += this.tuesday == true ? "1" : "0";
        dayOfWeek += this.wednesday == true ? "1" : "0";
        dayOfWeek += this.thursday == true ? "1" : "0";
        dayOfWeek += this.friday == true ? "1" : "0";
        dayOfWeek += this.saturday == true ? "1" : "0";
        System.out.println(dayOfWeek);
        return dayOfWeek;
    }
}
