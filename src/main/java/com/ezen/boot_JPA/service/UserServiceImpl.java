package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.UserDTO;
import com.ezen.boot_JPA.entity.AuthUser;
import com.ezen.boot_JPA.entity.User;
import com.ezen.boot_JPA.repository.AuthUserRepository;
import com.ezen.boot_JPA.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthUserRepository authUserRepository;

    // findAll = select * from user;

    @Transactional
    @Override
    public Long register(UserDTO userDTO) {
        String email = userRepository.save(convertDtoToEntity(userDTO)).getEmail();
        if(email != null){
//            AuthUser authUser = AuthUser.builder()
//                    .email(email)
//                    .auth("ROLE_USER")
//                    .build();
            Long id = authUserRepository.save(convertDtoToAuthEntity(userDTO)).getId();
            return id;
        }
        return null;
    }

    @Override
    public UserDTO selectEmail(String username) {
        Optional<User> optional = userRepository.findById(username);
        if(optional.isPresent()){
            List<AuthUser> authUserList = authUserRepository.findByEmail(username);
            UserDTO userDTO = convertEntityToDto(optional.get(), authUserList.stream().map(a -> convertEntityToAuthDto(a)).toList());
            log.info(">>>> userDTO > {}", userDTO);
            return userDTO;
        }
        return null;
    }

    @Override
    public boolean updateLastLogin(String authEmail) {
        Optional<User> optional = userRepository.findById(authEmail);
        if(optional.isPresent()){
            User user = optional.get();
            user.setLastLogin(LocalDateTime.now());
            String email = userRepository.save(user).getEmail();
            return email == null? false : true;
        }
        return false;
    }

    @Override
    public List<UserDTO> getList() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user : userList){
            List<AuthUser> authUserList = authUserRepository.findByEmail(user.getEmail());
            UserDTO userDTO = convertEntityToDto(user, authUserList.stream().map(a -> convertEntityToAuthDto(a)).toList());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public int update(UserDTO userDTO) {
        Optional<User> optional = userRepository.findById(userDTO.getEmail());
        if(optional.isPresent()) {
            User user = optional.get();
            user.setNickName(userDTO.getNickName());
            return userRepository.save(user).getEmail() != null? 1 : 0;
        }
        return 0;
    }

    @Override
    public int updateHasPwd(UserDTO userDTO) {
        Optional<User> optional = userRepository.findById(userDTO.getEmail());
        if (optional.isPresent()) {
            User user = optional.get();
            user.setNickName(userDTO.getNickName());
            user.setPwd(userDTO.getPwd());
            return userRepository.save(user).getEmail() != null ? 1 : 0;
        }
        return 0;
    }

    @Override
    public int delete(String email) {
        Optional<User> optional = userRepository.findById(email);
        List<AuthUser> authUserList = authUserRepository.findByEmail(email);
        if(optional.isPresent()){
            for(AuthUser authUser : authUserList){
                authUserRepository.deleteById(authUser.getId());
            }
            userRepository.deleteById(email);
        }

        Optional<User> result = userRepository.findById(email);
        return result.isEmpty()? 1 : 0;
    }


}
