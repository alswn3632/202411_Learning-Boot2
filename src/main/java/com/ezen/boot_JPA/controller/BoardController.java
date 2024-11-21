package com.ezen.boot_JPA.controller;

import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.dto.PagingVO;
import com.ezen.boot_JPA.entity.Board;
import com.ezen.boot_JPA.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(BoardDTO boardDTO){
        log.info(">>>> boardDTO > {}", boardDTO);
        // 일반적으로 insert, update, delete => return 1 row
        // jpa에서는 insert, update, delete => return id
        Long bno = boardService.insert(boardDTO);
        log.info(">>>> insert > {}", (bno>0? "성공" : "실패"));

        return "/index";
    }

//    @GetMapping("/list")
//    public String list(Model model){
//        List<BoardDTO> list = boardService.getList();
//        model.addAttribute("list", list);
//
//        return "/board/list";
//    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "pageNo",
            defaultValue = "0", required = false) int pageNo){

        Page<BoardDTO> list = boardService.getList(pageNo);

        PagingVO pgvo = new PagingVO(list);
        log.info(">>>> pgvo > {}", pgvo);
        model.addAttribute("list", list);
        model.addAttribute("pgvo", pgvo);

        return "/board/list";
    }

    @GetMapping("/detail")
    public String detail(Model model,@RequestParam("bno") Long bno){
        BoardDTO boardDTO = boardService.getDetail(bno);
        model.addAttribute("boardDTO", boardDTO);

        return "/board/detail";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO, RedirectAttributes redirectAttributes){
        Long bno = boardService.modify(boardDTO);
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return "redirect:/board/detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bno") Long bno){
        log.info(">>>> bno > {}", bno);
        boardService.delete(bno);
        return "redirect:/board/list";
    }

}
