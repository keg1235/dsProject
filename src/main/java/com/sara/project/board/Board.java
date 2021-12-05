package com.sara.project.board;

import com.sara.project.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="BOARD")
public class Board {
    @Id
    private Long id;

    @Column
    private String title;

    @Column
    private String memo;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    @Column
    private LocalDateTime inDate;

    @Column
    private LocalDateTime upDate;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="SUB_ID")
    private User sub;

}
