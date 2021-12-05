package com.sara.project.ddcmessage;

import com.sara.project.ddc.Ddc;

import com.sara.project.user.User;
import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="DDC_MESSAGE")
public class DdcMessage {

    @Id
    private Long id;

    private Long ddcId;

    private Long userId;

}
