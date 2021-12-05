package com.sara.project.message.dto;

import com.sara.project.ddc.Ddc;
import com.sara.project.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto {

    private Long id;
    private Long ddcId;
    private String message;
    private String useYn;


    public Message of(
            Long id,
            Ddc ddc
    ) {

        return Message.builder()
                .id(id)
                .ddc(ddc)
                .message(this.message)
                .build();
    }

    public Message of(
            Ddc ddc
    ) {

        return Message.builder()
                .id(this.id)
                .ddc(ddc)
                .message(this.message)
                .build();
    }
}
