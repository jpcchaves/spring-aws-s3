package com.springaws.springaws.application.service.user;

import com.springaws.springaws.application.dto.UserRequestDTO;
import com.springaws.springaws.application.dto.UserResponseDTO;
import com.springaws.springaws.application.utils.mapper.MapperUtils;
import com.springaws.springaws.domain.model.User;
import com.springaws.springaws.infra.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperUtils mapperUtils;

    public UserServiceImpl(UserRepository userRepository,
                           MapperUtils mapperUtils) {
        this.userRepository = userRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public List<UserResponseDTO> getUsersList() {
        List<User> usersList = userRepository.findAll();
        return mapperUtils.parseListObjects(usersList, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with the given id"));
        return mapperUtils.parseObject(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        User user = mapperUtils.parseObject(requestDTO, User.class);
        User createdUser = userRepository.save(user);
        return mapperUtils.parseObject(createdUser, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(Long userId,
                                      UserRequestDTO requestDTO) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with the given id"));

        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());

        User updatedUser = userRepository.save(user);

        return mapperUtils.parseObject(updatedUser, UserResponseDTO.class);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with the given id"));

        userRepository.deleteById(userId);
    }
}
