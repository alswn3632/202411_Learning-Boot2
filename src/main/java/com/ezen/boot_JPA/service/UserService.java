package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.AuthUserDTO;
import com.ezen.boot_JPA.dto.UserDTO;
import com.ezen.boot_JPA.entity.AuthUser;
import com.ezen.boot_JPA.entity.User;

import java.util.List;

public interface UserService {
    // convert 작업
    // UserDTO > User 변환

    default User convertDtoToEntity(UserDTO userDTO){
        return User.builder()
                .email(userDTO.getEmail())
                .pwd(userDTO.getPwd())
                .nickName(userDTO.getNickName())
                .lastLogin(userDTO.getLastLogin())
                .build();
    }

    default UserDTO convertEntityToDto(User user, List<AuthUserDTO> authUserDTOList){
        return UserDTO.builder()
                .email(user.getEmail())
                .pwd(user.getPwd())
                .nickName(user.getNickName())
                .lastLogin(user.getLastLogin())
                .regAt(user.getRegAt())
                .modAt(user.getModAt())
                .authList(authUserDTOList)
                .build();
    }

    // **
    default AuthUser convertDtoToAuthEntity(UserDTO userDTO){
        return AuthUser.builder()
                .email(userDTO.getEmail())
                .auth("ROLE_USER")
                .build();
    }

    // **
    default AuthUserDTO convertEntityToAuthDto(AuthUser authUser){
        return AuthUserDTO.builder()
                .email(authUser.getEmail())
                .auth(authUser.getAuth())
                .build();
    }

    default AuthUser convertDtoToEntity(AuthUserDTO authUserDTO){
        return AuthUser.builder()
                .id(authUserDTO.getId())
                .email(authUserDTO.getEmail())
                .auth(authUserDTO.getAuth())
                .build();
    }

    default AuthUserDTO convertEntityToDto(AuthUser authUser){
        return AuthUserDTO.builder()
                .id(authUser.getId())
                .email(authUser.getEmail())
                .auth(authUser.getAuth())
                .build();
    }

    Long register(UserDTO userDTO);

    UserDTO selectEmail(String username);

    boolean updateLastLogin(String authEmail);

    List<UserDTO> getList();

    int update(UserDTO userDTO);

    int updateHasPwd(UserDTO userDTO);

    int delete(String email);
}
