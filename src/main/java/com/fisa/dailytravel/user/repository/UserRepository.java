package com.fisa.dailytravel.user.repository;

import com.fisa.dailytravel.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUuid(String uuid);
}
