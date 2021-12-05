package com.sara.project.board.dto;

import com.sara.project.board.Board;
import com.sara.project.ddc.Ddc;
import com.sara.project.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {

    private Long id;
    private String title;
    private String memo;
    private String userId;
    private String subId;

    public Board of(
            Long id,
            User user,
            User sub
    ) {

        return Board.builder()
                .id(id)
                .title(this.title)
                .memo(this.memo)
                .user(user)
                .sub(sub)
                .build();
    }

    public Board of(
            User user,
            User sub
    ) {

        return Board.builder()
                .id(this.id)
                .title(this.title)
                .memo(this.memo)
                .user(user)
                .sub(sub)
                .build();
    }
}
