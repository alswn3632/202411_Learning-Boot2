package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.dto.CommentDTO;
import com.ezen.boot_JPA.entity.Board;
import com.ezen.boot_JPA.entity.Comment;
import com.ezen.boot_JPA.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;

    @Override
    public Long register(CommentDTO commentDTO) {
        return commentRepository.save(convertDtoToEntity(commentDTO)).getCno();
    }

    @Override
    public Page<CommentDTO> getList(Long bno, int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("cno").descending());
        Page<Comment> list = commentRepository.findByBno(bno, pageable);
        Page<CommentDTO> dtoList = list.map(c-> convertEntityToDto(c));
        return dtoList;
    }

    @Override
    public long delete(Long cno) {
        commentRepository.deleteById(cno);
        Optional<Comment> optional = commentRepository.findById(cno);
        return optional.map(Comment::getCno).orElse(0L);
//        if (optional.isEmpty()) {
//            return 1;
//        } else {
//            return 0;
//        }
    }

    @Override
    public Long modify(CommentDTO commentDTO) {
        Optional<Comment> optional = commentRepository.findById(commentDTO.getCno());
        if(optional.isPresent()){
            optional.get().setContent(commentDTO.getContent());
            commentRepository.save(optional.get());
            return optional.get().getCno();
        }
        return null;
    }
}
