package com.ezen.boot_JPA.controller;

import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.dto.CommentDTO;
import com.ezen.boot_JPA.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/comment/*")
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestBody CommentDTO commentDTO){
        log.info(">>>> comment > {}", commentDTO);
        Long cno = commentService.register(commentDTO);
        return cno>0? "1" : "0";
    }

    @ResponseBody
    @GetMapping(value = "/list/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CommentDTO> list(@PathVariable("bno") Long bno, @PathVariable("page") int page){
        Page<CommentDTO> list = commentService.getList(bno, page);
        log.info(">>>> list > {}", list);
        return list;
    }

    @ResponseBody
    @DeleteMapping(value = "/delete/{cno}")
    public String delete(@PathVariable("cno") Long cno){
        log.info(">>>> cno > {}", cno);
        long isOk = commentService.delete(cno);
        return isOk==0L? "1" : "0";
    }

    @ResponseBody
    @PutMapping("/modify")
    public String modify(@RequestBody CommentDTO commentDTO){
        log.info(">>>> commentVO > {}", commentDTO);
        Long cno = commentService.modify(commentDTO);
        return cno>0? "1" : "0";
    }


}
