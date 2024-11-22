package com.ezen.boot_JPA.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class BoardFileDTO {
    private BoardDTO boardDTO;
    private List<FileDTO> fileDTOList;
}
