package com.sara.project.aram;

import com.sara.project.aram.dto.AramRequestDto;
import com.sara.project.aram.dto.AramResponseDto;
import com.sara.project.aram.dto.AramSetRequestDto;
import com.sara.project.aram.dto.AramSetResponseDto;
import com.sara.project.eletron.Eletron;
import com.sara.project.eletron.dto.EletronResponseDto;
import com.sara.project.util.Const;
import com.sara.project.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AramServiceImpl implements AramService {

    @Autowired
    private AramRepository aramRepository;

    @Autowired
    private AramSetRepository aramSetRepository;


    @Override
    public List<AramResponseDto> getAramList() { // 팝업창에 보이는 곳
        List<Aram> arams = aramRepository.findByConfirmYn("N");
        List<AramResponseDto> aramResponseDtos
                = arams.stream().map(AramResponseDto::of)
                .collect(Collectors.toList());
        return aramResponseDtos;
    }

    @Override
    public List<AramResponseDto> getAramExcelList() { // 엑셀다운
        List<Aram> arams = aramRepository.findAll();
        List<AramResponseDto> aramResponseDtos
                = arams.stream().map(AramResponseDto::of)
                .collect(Collectors.toList());
        return aramResponseDtos;
    }

    @Override
    public AramSetResponseDto getAramSetList() {
        AramSet aramSet = aramSetRepository.findById(1l).orElse(null);
        return AramSetResponseDto.of(aramSet);
    }

    @Override
    public void save(AramSetRequestDto aramSetRequestDto) {
        AramSet aramSet = aramSetRequestDto.of(1l);
        aramSetRepository.save(aramSet);
    }

    @Override
    public void saveStatus(AramRequestDto aramRequestDto) {

        Aram aramSet = aramRequestDto.of(aramRepository.findMaxValue()+1);
        aramSet.setConfirmYn("N");
        aramRepository.save(aramSet);
    }

    @Transactional
    @Override
    public void saveClose(AramRequestDto aramRequestDto) {

        aramRepository.allUpdate();
    }
}
