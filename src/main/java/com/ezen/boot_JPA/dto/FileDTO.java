package com.ezen.boot_JPA.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class FileDTO {

    private String uuid;
    private String saveDir;
    private String fileName;
    private int fileType;
    private long fileSize;
    private Long bno;
    private LocalDateTime regAt, modAt;

}
