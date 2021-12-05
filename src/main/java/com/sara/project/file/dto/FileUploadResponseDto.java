package com.sara.project.file.dto;

import com.sara.project.file.FileUpload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.UnsupportedEncodingException;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponseDto {

    private Long id;
    private String src;

    public static FileUploadResponseDto of(
            FileUpload fileUpload
    ) {
        byte[] imagefile = fileUpload.getFileImg();
        byte[] encodeBase64 = Base64.encodeBase64(imagefile);
        String base64DataString = null;
        try {
            base64DataString = new String(encodeBase64 , "UTF-8");
        } catch (UnsupportedEncodingException e) {
            base64DataString = "";
        }
        return FileUploadResponseDto.builder()
                .id(fileUpload.getId())
                .src("data:image/"+fileUpload.getFileType()+";base64,"+base64DataString)
                .build();
    }
}
