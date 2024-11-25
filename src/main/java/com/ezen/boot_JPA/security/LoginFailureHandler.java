package com.ezen.boot_JPA.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMassage;

        if(exception instanceof BadCredentialsException){
            errorMassage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        }else if(exception instanceof InternalAuthenticationServiceException){
            errorMassage = "내부 시스템 문제로 로그인이 불가능합니다. 관리자에게 문의해주세요.";
        }else if(exception instanceof UsernameNotFoundException){
            errorMassage = "계정이 존재하지 않습니다. 아이디를 확인해주세요.";
        }else if(exception instanceof AuthenticationCredentialsNotFoundException){
            errorMassage = "인증 요청이 거부되었습니다. 관리자에게 문의해주세요.";
        }else {
            errorMassage = "관리자에게 문의해주세요.";
        }
        errorMassage = URLEncoder.encode(errorMassage, StandardCharsets.UTF_8);
        setDefaultFailureUrl("/user/login?error=true&exception=" + errorMassage);
        super.onAuthenticationFailure(request, response, exception);

    }
}
