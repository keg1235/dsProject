package com.sara.project.file;

import com.sara.project.file.dto.FileUploadResponseDto;
import com.sara.project.util.Const;
import com.sara.project.util.ResponseMessage;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Transactional
    @Override
    public boolean fileSave(List<MultipartFile> fileList) {
        boolean isPermissionFileExt = false;
        for (MultipartFile file : fileList) {

            StringBuilder sb = new StringBuilder();
            sb.append("D:/img/");
            sb.append(file.getOriginalFilename());
            String[] IMG_CHECKER = {"GIF", "JPEG", "JPG", "PNG"};
            String[] fileCheck = file.getOriginalFilename().split("\\.");
            String name = fileCheck[fileCheck.length - 1].toUpperCase();
            for (int i = 0; i < IMG_CHECKER.length; i++) {
                if (IMG_CHECKER[i].equals(name)) {
                    isPermissionFileExt = true;
                    break;
                }
            }

            if (isPermissionFileExt) {
                try {
                    FileUpload fileUpload = new FileUpload();
                    fileUpload.setFileImg(file.getBytes());
                    fileUpload.setId(fileUploadRepository.findMaxValue());
                    fileUpload.setFileType(name);
                    fileUploadRepository.save(fileUpload);
                    // convertBinary(file);
                } catch (Exception e) {

                    isPermissionFileExt =  false;
                    break;
                }

            }else{
                isPermissionFileExt =  false;
                break;
            }
            /*
            System.out.println(sb.toString());
            File targetFile = new File(sb.toString());
            try {
                InputStream fileStream = file.getInputStream();

                org.apache.commons.io.FileUtils.copyInputStreamToFile(fileStream, targetFile);

            } catch (IOException e) {
                if (e instanceof IOException) {
                    org.apache.commons.io.FileUtils.deleteQuietly(targetFile);
                }
            }*/
        }
        return isPermissionFileExt;
    }

    @Override
    public List<FileUploadResponseDto> fileUploadGet() {
        List<FileUpload> fileUploads = fileUploadRepository.findAll();
        List<FileUploadResponseDto> fileUploadResponseDtos
                = fileUploads.stream()
                .map(FileUploadResponseDto::of)
                .collect(Collectors.toList());
        return fileUploadResponseDtos;
    }


}
