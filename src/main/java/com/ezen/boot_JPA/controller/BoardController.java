package com.ezen.boot_JPA.controller;

import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.dto.BoardFileDTO;
import com.ezen.boot_JPA.dto.FileDTO;
import com.ezen.boot_JPA.dto.PagingVO;
import com.ezen.boot_JPA.handler.FileHandler;
import com.ezen.boot_JPA.handler.FileRemoveHandler;
import com.ezen.boot_JPA.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;
    private final FileHandler fileHandler;

    @GetMapping("/register")
    public void register(){}

//    @PostMapping("/register")
//    public String register(BoardDTO boardDTO){
//        log.info(">>>> boardDTO > {}", boardDTO);
//        // 일반적으로 insert, update, delete => return 1 row
//        // jpa에서는 insert, update, delete => return id
//        Long bno = boardService.insert(boardDTO);
//        log.info(">>>> insert > {}", (bno>0? "성공" : "실패"));
//
//        return "/index";
//    }

    @PostMapping("/register")
    public String register(BoardDTO boardDTO, @RequestParam(name="files",required = false) MultipartFile[] files){
        log.info(">>>> boardDTO > {}", boardDTO);
        List<FileDTO> flist = null;
        if(files != null && files[0].getSize()>0){
            // 파일 핸들러 작업 1. 실제 저장소에 저장 2. 파일 데이터가 담긴 flist 리턴
            flist = fileHandler.uploadfiles(files);
            log.info(">>>> flist > {}", flist);
        }
        Long bno = boardService.insert(new BoardFileDTO(boardDTO, flist));
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
    public String list(Model model,
                       @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                       @RequestParam(value = "type", required = false) String type,
                       @RequestParam(value = "keyword", required = false) String keyword){

        Page<BoardDTO> list = boardService.getList(pageNo, type, keyword);

        PagingVO pgvo = new PagingVO(list, type, keyword);
        log.info(">>>> list > {}", list);
        log.info(">>>> pgvo > {}", pgvo);

        model.addAttribute("list", list);
        model.addAttribute("pgvo", pgvo);

        return "/board/list";
    }

    @GetMapping("/detail")
    public String detail(Model model, @RequestParam("bno") Long bno){
        BoardFileDTO boardFileDTO = boardService.getDetail(bno);
        log.info(">>>> boardFileDTO > {}", boardFileDTO);
        model.addAttribute("boardFileDTO", boardFileDTO);

        return "/board/detail";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO, @RequestParam(name="files",required = false) MultipartFile[] files, RedirectAttributes redirectAttributes){
        List<FileDTO> flist = null;
        if(files != null && files[0].getSize()>0){
            flist = fileHandler.uploadfiles(files);
            log.info(">>>> flist > {}", flist);
        }

        Long bno = boardService.modify(new BoardFileDTO(boardDTO, flist));
        redirectAttributes.addAttribute("bno", bno);
        return "redirect:/board/detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bno") Long bno){
        log.info(">>>> bno > {}", bno);
        boardService.delete(bno);
        return "redirect:/board/list";
    }

    @ResponseBody
    @DeleteMapping(value="/file/{uuid}")
    public String fileDelete(@PathVariable("uuid") String uuid) {
        FileRemoveHandler fileRemoveHandler = new FileRemoveHandler();

        FileDTO fileDTO = boardService.getFile(uuid);
        int isOk = fileRemoveHandler.deleteFile(fileDTO);
        if(isOk>0){
            isOk = boardService.deleteFile(uuid);
        }

        return isOk>0? "1" : "0";
    }

}
