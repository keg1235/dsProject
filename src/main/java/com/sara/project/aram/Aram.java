package com.sara.project.aram;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="ARAM")
public class Aram {

    @Id
    private Long id;

    @Column
    private String name;

    @CreatedDate
    private LocalDateTime inDate;

    @Column
    private String status;

    @Column
    private String confirmYn;

    @Column
    private Long eletronId;


}
