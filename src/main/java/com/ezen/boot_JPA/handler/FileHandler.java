package com.ezen.boot_JPA.handler;

import com.ezen.boot_JPA.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileHandler {

    private final String UP_DIR = "D:\\_myproject\\_java\\_fileUpload\\";

    public List<FileDTO> uploadfiles (MultipartFile[] files){
        List<FileDTO> flist = new ArrayList<>();
        LocalDate date = LocalDate.now();
        String today = date.toString().replace("-", File.separator); // 2024-11-15를 2024\\11\\15로

        File folders = new File(UP_DIR, today);

        if(!folders.exists()){ // **월 **일의 폴더가 없다면 생성하자
            folders.mkdirs();
        }

        for(MultipartFile file : files){
            FileDTO fileDTO = new FileDTO();

            // saveDir
            fileDTO.setSaveDir(today); // 위에서 폴더를 생성했으니 여기에 저장할 수 있겠죠?

            // fileSize
            fileDTO.setFileSize(file.getSize());

            // fileName
            // 일반적으로 file.name이 경로를 포함하는 경우가 많다. 경로가 붙은 파일 이름의 끝 \\ 찾아서 그 뒤의 이름을 사용
            String originalFileName = file.getOriginalFilename();
            String onlyFileName = originalFileName.substring((originalFileName.lastIndexOf(File.separator)+1));
            fileDTO.setFileName(onlyFileName);

            // uuid
            UUID uuid = UUID.randomUUID();
            String uuidStr = uuid.toString();
            fileDTO.setUuid(uuidStr);

            // 실제 파일 저장
            String fileName = uuidStr + "_" + onlyFileName;
            File storeFile = new File(folders, fileName);

            try {
                file.transferTo(storeFile);
                // fileType
                if(isImgFile(storeFile)){
                    fileDTO.setFileType(1);
                    File thumbnail = new File(folders, uuidStr+"_th_"+onlyFileName);
                    Thumbnails.of(storeFile).size(100,100).toFile((thumbnail));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // flist에 fvo 객체 저장
            flist.add(fileDTO);
        }

        // 각각의 파일에 대한 fvo를 담은 flists 리턴
        return flist;
    }

    private boolean isImgFile(File file) throws IOException {
        String mmdType = new Tika().detect(file);
        return mmdType.startsWith("image");
    }

}
