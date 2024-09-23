package com.fisa.dailytravel.user.repository;

import com.fisa.dailytravel.config.CustomDataJpaTest;
import com.fisa.dailytravel.user.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@CustomDataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void 사용자_저장_및_조회_테스트() {
        //given
        User user = new User();
        user.setUuid("1");
        user.setEmail("test@test.com");
        user.setNickname("test");
        user.setIsDeleted(false);

        //when
        User savedUser = userRepository.save(user);

        //then
        Optional<User> findUser = userRepository.findByUuid("1");
        assertTrue(findUser.isPresent());
        assertEquals("test", findUser.get().getNickname());
        assertEquals("test@test.com", findUser.get().getEmail());
        assertFalse(findUser.get().getIsDeleted());
        assertNotNull(savedUser.getId(), "Generated ID는 반드시 존재해야 합니다.");
    }


}