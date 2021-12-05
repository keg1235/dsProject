package com.sara.project.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    @Query(value = "SELECT IFNULL(MAX(id),0)+1 FROM File_Upload", nativeQuery = true)
    Long findMaxValue();

}
