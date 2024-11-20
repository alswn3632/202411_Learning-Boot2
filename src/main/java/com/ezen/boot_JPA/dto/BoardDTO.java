package com.ezen.boot_JPA.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class BoardDTO {

    private Long bno; // DTO 또한 Long 형식으로 사용
    private String title;
    private String writer;
    private String content;
    private LocalDateTime regAt, modAt;

}
