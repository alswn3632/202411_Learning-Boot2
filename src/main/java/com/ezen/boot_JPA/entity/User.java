package com.ezen.boot_JPA.entity;

import com.ezen.boot_JPA.dto.AuthUserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
public class User extends TimeBase{

    @Id
    @Column(length = 200, nullable = false)
    private String email;

    @Column(length = 200, nullable = false)
    private String pwd;

    @Column(name = "nick_name", length = 100)
    private String nickName;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

}
