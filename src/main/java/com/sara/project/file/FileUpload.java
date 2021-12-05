package com.sara.project.file;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Clob;
import java.util.Base64;

@Entity
@Table(name="File_Upload")
@Getter
@Setter
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class FileUpload {

    @Id
    private Long id;


    @Column
    private String fileType;

    @Lob
    @Column
    private byte[] fileImg;

}
