package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.dto.CommentDTO;
import com.ezen.boot_JPA.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    default Comment convertDtoToEntity(CommentDTO commentDTO){
        return Comment.builder()
                .cno(commentDTO.getCno())
                .bno(commentDTO.getBno())
                .writer(commentDTO.getWriter())
                .content(commentDTO.getContent())
                .build();
    }

    default CommentDTO convertEntityToDto(Comment comment){
        return CommentDTO.builder()
                .cno(comment.getCno())
                .bno(comment.getBno())
                .writer(comment.getWriter())
                .content(comment.getContent())
                .regAt(comment.getRegAt())
                .modAt(comment.getModAt())
                .build();
    }

    Long register(CommentDTO commentDTO);

    Page<CommentDTO> getList(Long bno, int page);

    long delete(Long cno);

    Long modify(CommentDTO commentDTO);
}
