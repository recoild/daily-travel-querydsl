package com.fisa.dailytravel.user.repository;

import com.fisa.dailytravel.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUuid(String uuid);
}
