package com.akyc.oauth2.repository;

import com.akyc.oauth2.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findUserByEmailAndProvider(String email, String provider);

}
