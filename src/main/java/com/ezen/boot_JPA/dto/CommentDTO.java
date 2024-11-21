package com.ezen.boot_JPA.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class CommentDTO {

    private Long cno;
    private Long bno;
    private String writer;
    private String content;
    private LocalDateTime regAt, modAt;

}
