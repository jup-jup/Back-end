package com.jupjup.www.jupjup.user.repository;

import com.jupjup.www.jupjup.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserEmailAndProviderKey(String userEmail, String providerKey);
}
