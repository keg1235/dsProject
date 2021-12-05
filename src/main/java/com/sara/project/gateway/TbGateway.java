package com.sara.project.gateway;

import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="TB_GATEWAY")
public class TbGateway {

        @Id
        private Long id;

        @Column
        private String ipAddress;

        @Column
        private Integer port;

        @Column
        private String location;

        @Column
        private String memo;

}
