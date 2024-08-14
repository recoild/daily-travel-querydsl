package com.fisa.dailytravel.user.service;

import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void signin(UserCreateRequest userCreateRequest) throws Exception {
        User user = userRepository.findByUuid(userCreateRequest.getUuid());
        Optional<User> userOptional = Optional.ofNullable(user);
        if (userOptional.isEmpty())
            user = new User();

        String nickname = "user_" + new Date().getTime();

        user.setUuid(userCreateRequest.getUuid());
        user.setNickname(nickname);
        user.setEmail(userCreateRequest.getEmail());
        user.setProfileImagePath(userCreateRequest.getPicture());
        user.setIsDeleted(false);
        userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return null;
    }
}
