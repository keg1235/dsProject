package com.sara.project.dcpReport;

import com.sara.project.dcp.DcpRepository;
import com.sara.project.dcp.DcpService;
import com.sara.project.dcpReport.dto.DcpReportRequestDto;
import com.sara.project.dcpReport.dto.DcpReportResponseDto;
import com.sara.project.dcpSet.DcpSet;
import com.sara.project.dcpSet.DcpSetRepository;
import com.sara.project.eletron.Eletron;
import com.sara.project.eletron.EletronRepository;
import com.sara.project.eletron.EletronService;
import com.sara.project.eletron.dto.EletronResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DcpReportServiceImpl implements DcpReportService {

    @Autowired
    private DcpReportRepository dcpReportRepository;


    @Autowired
    private DcpSetRepository dcpSetRepository;



    @Override
    public List<DcpReportResponseDto> getdcpReportResponseDtoList() {
        List<DcpReport> dcpReports = dcpReportRepository.findAll();
        List<DcpReportResponseDto> dcpReportsList
                = dcpReports.stream().map(DcpReportResponseDto::of)
                .collect(Collectors.toList());
        return dcpReportsList;
    }

    @Override
    public List<DcpReportResponseDto> getReportExcelList(String std, String end) {
        List<DcpReport> dcpReports = dcpReportRepository.findExcelData(std,end);
        List<DcpReportResponseDto> dcpReportsList
                = dcpReports.stream().map(DcpReportResponseDto::of)
                .collect(Collectors.toList());
        return dcpReportsList;
    }

    @Transactional
    @Override
    public void deleteAll() {
        dcpReportRepository.deleteAll();
    }

    @Override
    public void save(DcpReportRequestDto dcpReportRequestDto) {

        Long id = dcpReportRepository.findMaxValue()+1;
        DcpReport dcpReport = dcpReportRequestDto.of(id);
        dcpReportRepository.save(dcpReport);
    }
}
