package com.jupjup.www.jupjup.domain.repository;

import com.jupjup.www.jupjup.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByName(String username);
    boolean existsByUserEmail(String userEmail);
    Optional<User> findByUserEmail(String userEmil);
    User findByUserEmailAndProviderKey(String userEmail, String providerKey);

}
