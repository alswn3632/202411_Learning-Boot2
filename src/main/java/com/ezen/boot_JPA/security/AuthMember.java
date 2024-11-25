package com.ezen.boot_JPA.security;

import com.ezen.boot_JPA.dto.UserDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class AuthMember extends User {

    private UserDTO userDTO;

    public AuthMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthMember(UserDTO userDTO) {
        super(userDTO.getEmail(),
                userDTO.getPwd(),
                userDTO.getAuthList()
                        .stream()
                        .map(a -> new SimpleGrantedAuthority(a.getAuth()))
                        .collect(Collectors.toList())
        );
        this.userDTO = userDTO;
    }
}
