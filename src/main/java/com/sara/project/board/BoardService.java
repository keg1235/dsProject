package com.sara.project.board;

import com.sara.project.board.dto.BoardRequestDto;
import com.sara.project.board.dto.BoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    List<BoardResponseDto> getBoardList();

    Page<BoardResponseDto> getBoardPage(Pageable pageRequest);

    BoardResponseDto get(Long id);

    void save(BoardRequestDto boardRequestDto);

    void update(Long id, BoardRequestDto boardRequestDto);

    void delete(Long id);
}
