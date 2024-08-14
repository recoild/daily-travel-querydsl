package com.fisa.dailytravel.user.repository;

import com.fisa.dailytravel.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUuid(String uuid);
}
