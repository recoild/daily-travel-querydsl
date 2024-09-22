package com.fisa.dailytravel.user.repository;

import com.fisa.dailytravel.config.TestcontainersConfiguration;
import com.fisa.dailytravel.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@ActiveProfiles("test")
@Import(TestcontainersConfiguration.class)
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