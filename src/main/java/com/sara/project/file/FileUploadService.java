package com.sara.project.file;

import com.sara.project.file.dto.FileUploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    boolean fileSave(List<MultipartFile> fileList);

    List<FileUploadResponseDto> fileUploadGet();
}
