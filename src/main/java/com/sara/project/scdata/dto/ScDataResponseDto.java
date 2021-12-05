package com.sara.project.scdata.dto;


import com.sara.project.scdata.ScData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ScDataResponseDto {

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

    public static ScDataResponseDto of(
            ScData scdata
    ) {



        return ScDataResponseDto.builder()
                .id(scdata.getId())
                .name(scdata.getName())
                .memo(scdata.getMemo())
                .useYn(scdata.getUseYn())
                .onOff(scdata.getOnOff())
                .startDate(scdata.getStartDate())
                .endDate(scdata.getEndDate())
                .sunday(setDayOfWeek("sunday",scdata.getWeeks()))
                .monday(setDayOfWeek("monday",scdata.getWeeks()))
                .tuesday(setDayOfWeek("tuesday",scdata.getWeeks()))
                .wednesday(setDayOfWeek("wednesday",scdata.getWeeks()))
                .thursday(setDayOfWeek("thursday",scdata.getWeeks()))
                .friday(setDayOfWeek("friday",scdata.getWeeks()))
                .saturday(setDayOfWeek("saturday",scdata.getWeeks()))
                .waitTime(scdata.getWaitTime())
                .time(scdata.getHour()+":"+scdata.getMin())

                .build();
    }


    private static boolean setMovieCheck(String urlStr){

        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            return false;
        }
        try {
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            int responseCode = huc.getResponseCode();
            if (responseCode != 200) return false;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean setDayOfWeek(String week, String getdayofweek){
        boolean result = false;

        switch (week){
            case "sunday":

                result = getdayofweek.charAt(0) == '0'? false : true;
                break;
            case "monday":

                result = getdayofweek.charAt(1) == '0'? false : true;
                break;
            case "tuesday":

                result = getdayofweek.charAt(2) == '0'? false : true;
                break;
            case "wednesday":

                result = getdayofweek.charAt(3) == '0'? false : true;
                break;
            case "thursday":

                result = getdayofweek.charAt(4) == '0'? false : true;
                break;
            case "friday":

                result = getdayofweek.charAt(5) == '0'? false : true;
                break;
            case "saturday":

                result = getdayofweek.charAt(6) == '0'? false : true;
                break;
        }
        System.out.println(result);
        return result;
    }
}
