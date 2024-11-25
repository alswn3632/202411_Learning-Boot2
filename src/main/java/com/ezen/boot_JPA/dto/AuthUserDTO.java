package com.ezen.boot_JPA.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class AuthUserDTO {

    private Long id;
    private String email;
    private String auth;

}
