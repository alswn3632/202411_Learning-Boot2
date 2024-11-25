package com.ezen.boot_JPA.controller;

import com.ezen.boot_JPA.dto.UserDTO;
import com.ezen.boot_JPA.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Slf4j
@RequestMapping("/user/*")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public void join(){}

    @PostMapping("/register")
    public String register(UserDTO userDTO){
        userDTO.setPwd(passwordEncoder.encode(userDTO.getPwd()));
        Long id = userService.register(userDTO);

        return "/index";
    }

    @GetMapping("/login")
    public void login(@RequestParam(value = "error", required = false) String error,
                      @RequestParam(value = "exception", required = false) String exception,
                      Model model){
        // 에러와 예외 값을 담아 화면으로 전달
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
    }

    @GetMapping("/list")
    public String list(Model m){
        List<UserDTO> list = userService.getList();
        m.addAttribute("list", list);

        return "/user/list";
    }

    @GetMapping("/modify")
    public void modify(){}

    @PostMapping("/modify")
    public String modify(UserDTO userDTO){
        int isOk;
        if(userDTO.getPwd() == null || userDTO.getPwd().equals("")){
            isOk = userService.update(userDTO);
        }else{
            userDTO.setPwd(passwordEncoder.encode(userDTO.getPwd()));
            isOk = userService.updateHasPwd(userDTO);
        }
        log.info(">>>> user update > {}",(isOk != 0? "성공":"실패"));

        return "redirect:/user/logout";

    }
}
