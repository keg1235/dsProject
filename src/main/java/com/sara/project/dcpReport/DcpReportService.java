package com.sara.project.dcpReport;

import com.sara.project.dcpReport.dto.DcpReportRequestDto;
import com.sara.project.dcpReport.dto.DcpReportResponseDto;

import java.util.List;

public interface DcpReportService {
    List<DcpReportResponseDto> getdcpReportResponseDtoList();

    List<DcpReportResponseDto> getReportExcelList(String std, String end);

    void deleteAll();

    void save(DcpReportRequestDto dcpReportRequestDto);
}
