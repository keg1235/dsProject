package com.sara.project.qna.dto;

import com.sara.project.board.Board;
import com.sara.project.ddc.Ddc;
import com.sara.project.qna.Qna;
import com.sara.project.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QnaRequestDto {

    private Long id;
    private String title;
    private String memo;
    private String userId;
    private String subId;

    public Qna of(
            Long id,
            User user,
            User sub
    ) {

        return Qna.builder()
                .id(id)
                .title(this.title)
                .memo(this.memo)
                .user(user)
                .memo(this.memo)
                .sub(sub)
                .build();
    }

    public Qna of(
            User user,
            User sub
    ) {

        return Qna.builder()
                .id(this.id)
                .title(this.title)
                .memo(this.memo)
                .user(user)
                .memo(this.memo)
                .sub(sub)
                .build();
    }
}
