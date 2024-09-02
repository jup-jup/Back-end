package com.jupjup.www.jupjup.user.repository;

import com.jupjup.www.jupjup.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByName(String username);
    boolean existsByUserEmail(String userEmail);
    Optional<User> findByUserEmail(String userEmil);
    User findByUserEmailAndProviderKey(String userEmail, String providerKey);

}
