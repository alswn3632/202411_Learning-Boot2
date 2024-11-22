package com.ezen.boot_JPA.handler;

import com.ezen.boot_JPA.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class FileRemoveHandler {
    private final String BASE_PATH = "D:\\_myproject\\_java\\_fileUpload\\";

    public int deleteFile(FileDTO fileDTO) {
        boolean isDel = false;

        File dir = new File(BASE_PATH, fileDTO.getSaveDir());

        // 원본 파일 삭제
        File file = new File( dir, fileDTO.getUuid() + "_" + fileDTO.getFileName());

        if(file.exists()) {
            log.info(">>>> FileRemoveHandler: delete file > {}", fileDTO.getUuid() + "_" + fileDTO.getFileName());
            isDel = file.delete();
        }

        // 썸네일 파일 삭제
        if(fileDTO.getFileType() > 0){
            File thFile = new File(dir, fileDTO.getUuid() + "_th_" + fileDTO.getFileName());
            if(thFile.exists()){
                log.info(">>>> FileRemoveHandler: delete file > {}", fileDTO.getUuid() + "_th_" + fileDTO.getFileName());
                isDel = thFile.delete();
            }
        }

        return isDel? 1:0;
    }

}
