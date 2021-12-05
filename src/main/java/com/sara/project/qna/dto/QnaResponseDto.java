package com.sara.project.qna.dto;

import com.sara.project.board.Board;
import com.sara.project.board.dto.BoardResponseDto;
import com.sara.project.dcp.Dcp;
import com.sara.project.dcp.dto.DcpResponseDto;
import com.sara.project.qna.Qna;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class QnaResponseDto {

    private Long id;
    private String title;
    private String memo;
    private String userId;
    private String subId;

    public static QnaResponseDto of(
            Qna qna
    ) {
        return QnaResponseDto.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .memo(qna.getMemo())
                .userId(qna.getUser().getEmail())
                .subId(qna.getSub().getEmail())
                .build();
    }
}
