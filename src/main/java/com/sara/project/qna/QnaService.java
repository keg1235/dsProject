package com.sara.project.qna;

import com.sara.project.qna.dto.QnaRequestDto;
import com.sara.project.qna.dto.QnaResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QnaService {
    List<QnaResponseDto> getQnaList();

    Page<QnaResponseDto> getQnaPage(Pageable pageRequest);

    QnaResponseDto get(Long id);

    void save(QnaRequestDto qnaRequestDto);

    void update(Long id, QnaRequestDto qnaRequestDto);

    void delete(Long id);
}
