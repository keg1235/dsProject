package com.sara.project.qna;

import com.sara.project.qna.dto.QnaRequestDto;
import com.sara.project.qna.dto.QnaResponseDto;
import com.sara.project.user.User;
import com.sara.project.user.UserRepository;
import com.sara.project.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QnaServiceImpl implements QnaService{
    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<QnaResponseDto> getQnaList() {
        List<Qna> qnaList = qnaRepository.findAll();
        List<QnaResponseDto> qnaResponseDtoList
                = qnaList.stream().map(QnaResponseDto::of).collect(Collectors.toList());
        return qnaResponseDtoList;
    }

    @Override
    public Page<QnaResponseDto> getQnaPage(Pageable pageRequest) {
        Page<Qna> qnas = qnaRepository.findAll(pageRequest);

        Page<QnaResponseDto> qnaResponseDtoPage = qnas.map(
                qna -> QnaResponseDto.of(qna)
        );
        return qnaResponseDtoPage;
    }

    @Override
    public QnaResponseDto get(Long id) {
        Optional<Qna> qnaOptional = qnaRepository.findById(id);
        Qna qna = null;
        if(qnaOptional.isPresent()) {
            qna = qnaOptional.get();
        }
        QnaResponseDto qnaResponseDto = QnaResponseDto.of(qna);
        return qnaResponseDto;
    }

    @Override
    public void save(QnaRequestDto qnaRequestDto) {
        try {
            Long generatedId = qnaRepository.findMaxValue() + 1;
            User user = userRepository.findByEmail(qnaRequestDto.getUserId());
            User sub = userRepository.findByEmail(qnaRequestDto.getSubId());
            Qna qna = qnaRequestDto.of(generatedId,user,sub);
            qnaRepository.save(qna);
        } catch (Exception e) {
            throw new ServiceException("QnaService", "save", e.toString());
        }
    }

    @Override
    public void update(Long id, QnaRequestDto qnaRequestDto) {
        try {
            User user = userRepository.findByEmail(qnaRequestDto.getUserId());
            User sub = userRepository.findByEmail(qnaRequestDto.getSubId());
            Qna qna = qnaRequestDto.of(user,sub);
            qnaRepository.save(qna);
        } catch (Exception e) {
            throw new ServiceException("QnaService", "update", e.toString());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            qnaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("QnaService", "delete", e.toString());
        }
    }
}
