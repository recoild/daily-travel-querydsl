package com.fisa.dailytravel.user.service;

import com.fisa.dailytravel.user.dto.UserCreateRequest;
import com.fisa.dailytravel.user.models.User;
import com.fisa.dailytravel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void createUser(UserCreateRequest userCreateRequest) {
        try {
            User user = new User();

            //날짜에서 yymmddhhmmss 형식. user_를 붙여준다.
            String nickname = "user_" + new Date().getTime();

            user.setUuid(userCreateRequest.getUuid());
            user.setNickname(nickname);
            user.setEmail(userCreateRequest.getEmail());
            user.setProfileImagePath("");
            user.setIsDeleted(false);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(Long id) {
        return null;
    }
}
