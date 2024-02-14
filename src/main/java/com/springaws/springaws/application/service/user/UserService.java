package com.springaws.springaws.application.service.user;

import com.springaws.springaws.application.dto.UserRequestDTO;
import com.springaws.springaws.application.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getUsersList();
    UserResponseDTO getUserById(Long userId);
    UserResponseDTO createUser(UserRequestDTO requestDTO);
    UserResponseDTO updateUser(Long userId, UserRequestDTO requestDTO);
    void deleteUser(Long userId);
}
