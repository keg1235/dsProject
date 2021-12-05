package com.sara.project.ddcmessage.ddcMessageDto;

import com.sara.project.dcp.Dcp;
import com.sara.project.ddc.Ddc;
import com.sara.project.ddcmessage.DdcMessage;
import com.sara.project.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DdcMessageRequestDto {

    private Long id;
    private Long ddcId;
    private Long userId;
    private String email;

    public DdcMessage of(
            Long id,
            User user
    ) {

        return DdcMessage.builder()
                .id(id)
                .ddcId(this.ddcId)
                .userId(user.getId())
                .build();
    }
}
