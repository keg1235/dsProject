package com.sara.project.message;

import com.sara.project.ddc.Ddc;
import com.sara.project.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="MESSAGE")
public class Message {
    @Id
    private Long id;

    @ManyToOne(targetEntity = Ddc.class, fetch = FetchType.LAZY)
    @JoinColumn(name="DDC_ID")
    private Ddc ddc;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    @Column
    private String message;

    @CreatedDate
    @Column(columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private LocalDateTime dateSystem;



}
