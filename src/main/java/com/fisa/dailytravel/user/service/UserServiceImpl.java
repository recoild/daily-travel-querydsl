package com.fisa.dailytravel.user.service;

import com.fisa.dailytravel.global.config.S3Uploader;
import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.dto.UserUpdateRequest;
import com.fisa.dailytravel.user.dto.UserUpdateResponse;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    @Override
    @Transactional
    public void signin(UserCreateRequest userCreateRequest) throws Exception {
        User user = userRepository.findByUuid(userCreateRequest.getUuid());
        Optional<User> userOptional = Optional.ofNullable(user);
        if (userOptional.isEmpty()){
            user = new User();
            String nickname = "user_" + new Date().getTime();

            user.setUuid(userCreateRequest.getUuid());
            user.setNickname(nickname);
            user.setEmail(userCreateRequest.getEmail());
            user.setProfileImagePath(userCreateRequest.getPicture());
            user.setIsDeleted(false);
        }

        userRepository.save(user);
    }

    @Override
    public User getUser(String uuid) throws Exception {
        return userRepository.findByUuid(uuid);
    }

    @Override
    @Transactional
    public UserUpdateResponse updateUser(String uuid, UserUpdateRequest userUpdateRequest) throws IOException {
        User user = userRepository.findByUuid(uuid);
        Optional<User> userOptional = Optional.ofNullable(user);
        if (userOptional.isEmpty())
            return null;

        user.setNickname(userUpdateRequest.getNickname());
        MultipartFile imageFile = userUpdateRequest.getProfileImageFile();
        Optional<MultipartFile> imageFileOptional = Optional.ofNullable(imageFile);
        if (imageFileOptional.isPresent()) {
            String imageUrl = s3Uploader.uploadImage("user", user.getNickname(), user.getId(), imageFile);
            user.setProfileImagePath(imageUrl);
        }

        userRepository.save(user);


        return UserUpdateResponse.builder()
                .nickname(user.getNickname())
                .profileImagePath(user.getProfileImagePath())
                .build();
    }
}
