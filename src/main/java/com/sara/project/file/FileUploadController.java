package com.sara.project.file;

import com.sara.project.file.dto.FileUploadResponseDto;
import com.sara.project.util.Const;
import com.sara.project.util.ResponseMessage;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/fileUpload")
public class FileUploadController {


    @Autowired
    private FileUploadService fileUploadService;


    @ApiOperation(value = "파일다중업로드")
    @PostMapping("")
    public ResponseEntity<ResponseMessage> filesUpload(
            @RequestParam("files") List<MultipartFile> files
    ) {
       boolean result =  fileUploadService.fileSave(files);
        if (result){
            ResponseMessage responseMessage = new ResponseMessage(Const.success, "", "");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }else{
            ResponseMessage responseMessage = new ResponseMessage(Const.serverError, "", "해당파일이 이미지가 아닙니다.");
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @ApiOperation(value = "파일다중업로드")
    @GetMapping("")
    public ResponseEntity<ResponseMessage> getfilesUpload(

    ) {
        List<FileUploadResponseDto> fileUploadResponseDtos= fileUploadService.fileUploadGet();

        ResponseMessage responseMessage = new ResponseMessage(Const.success, fileUploadResponseDtos, "");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

}
