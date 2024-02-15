package com.springaws.springaws.application.service.user;

import com.springaws.springaws.application.dto.UserRequestDTO;
import com.springaws.springaws.application.dto.UserResponseDTO;
import com.springaws.springaws.application.service.s3.S3Service;
import com.springaws.springaws.application.utils.mapper.MapperUtils;
import com.springaws.springaws.domain.model.User;
import com.springaws.springaws.infra.config.aws.s3.S3Buckets;
import com.springaws.springaws.infra.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    private final MapperUtils mapperUtils;

    public UserServiceImpl(UserRepository userRepository,
                           S3Service s3Service,
                           S3Buckets s3Buckets,
                           MapperUtils mapperUtils) {
        this.userRepository = userRepository;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public List<UserResponseDTO> getUsersList() {
        List<User> usersList = userRepository.findAll();
        return mapperUtils.parseListObjects(usersList, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        User user = findUserById(userId);
        return mapperUtils.parseObject(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        User user = mapperUtils.parseObject(requestDTO, User.class);
        User createdUser = saveUser(user);
        return mapperUtils.parseObject(createdUser, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(Long userId,
                                      UserRequestDTO requestDTO) {
        User user = findUserById(userId);

        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());

        User updatedUser = saveUser(user);

        return mapperUtils.parseObject(updatedUser, UserResponseDTO.class);
    }

    @Override
    public void deleteUser(Long userId) {
        findUserById(userId);

        userRepository.deleteById(userId);
    }

    @Override
    public void uploadUserProfileImage(MultipartFile file,
                                       Long userId) {
        User user = findUserById(userId);
        String profileImageId = UUID.randomUUID().toString();

        try {

            s3Service.putObject(
                    s3Buckets.getUsers(),
                    "profile-images/%s/%s".formatted(userId, profileImageId),
                    file.getBytes()
            );

        } catch (IOException e) {
            logger.error("IOException occurred trying to upload a file", e);
            throw new RuntimeException(e);
        }

        user.setProfileImage(profileImageId);
        saveUser(user);
    }

    @Override
    public byte[] getUserProfileImage(Long userId) {
        User user = findUserById(userId);

        if (user.getProfileImage().isBlank() || Objects.isNull(user.getProfileImage())) {
            throw new RuntimeException("User doesn't have profile image");
        }

        return s3Service
                .getObject(
                        s3Buckets.getUsers(),
                        "profile-images/%s/%s".formatted(userId, user.getProfileImage()
                        )
                );
    }

    private User findUserById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with the given id"));
    }

    private User saveUser(User user) {
        return userRepository.save(user);
    }
}
