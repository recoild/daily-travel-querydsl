package com.fisa.dailytravel.user.repository;

import com.fisa.dailytravel.config.CustomDataJpaTest;
import com.fisa.dailytravel.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
//@ActiveProfiles("test")
//@Import(TestcontainersConfiguration.class)
@CustomDataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        List<User> users = List.of(
                User.builder().uuid("1").email("1@example.com").nickname("1").isDeleted(false).build(),
                User.builder().uuid("2").email("2@example.com").nickname("2").isDeleted(false).build(),
                User.builder().uuid("3").email("3@example.com").nickname("3").isDeleted(false).build()
        );

        userRepository.saveAll(users);
    }

    @Test
    void testSaveEntity() {
        assertThat(userRepository.findAll()).hasSize(3);
    }
}