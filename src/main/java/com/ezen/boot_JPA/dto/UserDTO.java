package com.ezen.boot_JPA.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class UserDTO {

    private String email;
    private String pwd;
    private String nickName;
    private LocalDateTime lastLogin;
    private LocalDateTime regAt, modAt;
    private List<AuthUserDTO> authList;
}
