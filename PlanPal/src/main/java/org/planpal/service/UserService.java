package org.planpal.service;

import org.planpal.domain.User;
import org.planpal.dto.UserDTO;
import org.planpal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public UserDTO findByUserId(int userId) {
        return userRepository.findUserByUserId(userId);
    }

    public void save(UserDTO userDTO) {
        UserDTO existingUser = userRepository.findUserByUsername(userDTO.getUsername());
        if (existingUser == null) {
            User user = dtoToDomain(userDTO);
            userRepository.saveUser(user);
        } else {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }
    }

    public void delete(int userId) {
        userRepository.deleteUser(userId);
    }

    public void changePassword(UserDTO userDTO) {
        UserDTO existingUserDTO = userRepository.findUserByUsername(userDTO.getUsername());
        if (existingUserDTO != null) {
            User user = dtoToDomain(userDTO);
            userRepository.changePassword(user); // 비밀번호만 업데이트하도록 변경
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
    }

    private User dtoToDomain(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // 비밀번호 암호화
        return user;
    }
}
