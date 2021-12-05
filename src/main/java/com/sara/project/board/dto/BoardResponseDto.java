package com.sara.project.board.dto;

import com.sara.project.board.Board;
import com.sara.project.dcp.Dcp;
import com.sara.project.dcp.dto.DcpResponseDto;
import com.sara.project.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;
    private String memo;
    private String userId;
    private String subId;

    public static BoardResponseDto of(
            Board board
    ) {

        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .memo(board.getMemo())
                .userId(board.getUser().getEmail())
                .subId(board.getSub().getEmail())
                .build();
    }
}
