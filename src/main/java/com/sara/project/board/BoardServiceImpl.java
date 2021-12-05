package com.sara.project.board;

import com.sara.project.board.dto.BoardRequestDto;
import com.sara.project.board.dto.BoardResponseDto;
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
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public List<BoardResponseDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardResponseDto> boardResponseDtoList
                = boardList.stream().map(BoardResponseDto::of).collect(Collectors.toList());
        return boardResponseDtoList;
    }

    @Override
    public Page<BoardResponseDto> getBoardPage(Pageable pageRequest) {
        Page<Board> boards = boardRepository.findAll(pageRequest);

        Page<BoardResponseDto> boardResponseDtoPage = boards.map(
                board -> BoardResponseDto.of(board)
        );
        return boardResponseDtoPage;
    }

    @Override
    public BoardResponseDto get(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        Board board = null;
        if(boardOptional.isPresent()) {
            board = boardOptional.get();
        }
        BoardResponseDto boardResponseDto = BoardResponseDto.of(board);
        return boardResponseDto;
    }

    @Override
    public void save(BoardRequestDto boardRequestDto) {
        try {
            Long generatedId = boardRepository.findMaxValue() + 1;
            User user = userRepository.findByEmail(boardRequestDto.getUserId());
            User sub = userRepository.findByEmail(boardRequestDto.getSubId());
            Board board = boardRequestDto.of(generatedId,user, sub);
            boardRepository.save(board);
        } catch (Exception e) {
            throw new ServiceException("BoardService", "save", e.toString());
        }
    }

    @Override
    public void update(Long id, BoardRequestDto boardRequestDto) {
        try {

            User user = userRepository.findByEmail(boardRequestDto.getUserId());
            User sub = userRepository.findByEmail(boardRequestDto.getSubId());
            Board board = boardRequestDto.of(id,user,sub);
            boardRepository.save(board);
        } catch (Exception e) {
            throw new ServiceException("BoardService", "update", e.toString());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("BoardService", "delete", e.toString());
        }
    }
}
