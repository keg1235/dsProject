package com.sara.project.user;

import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="User")
public class User {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String poneNum;

    @Column
    private String adminYn;

    @Column
    private String idUseYn;
}
