package com.ezen.boot_JPA.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
public class File extends TimeBase{

    @Id
    private String uuid;

    @Column(name = "save_dir", nullable = false)
    private String saveDir;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false, columnDefinition = "integer default 0")
    private int fileType;

    @Column(name = "file_size")
    private long fileSize;

    // 설정할게 없다면 어노테이션을 붙이지 않아도 됨!
    private Long bno;

}
