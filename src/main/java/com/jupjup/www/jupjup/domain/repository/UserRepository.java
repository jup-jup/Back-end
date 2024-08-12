package com.jupjup.www.jupjup.domain.repository;

import com.jupjup.www.jupjup.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByName(String username);
    boolean existsByUserEmail(String userEmail);
    UserEntity findByUserEmail(String userEmil);
    UserEntity findByUserEmailAndProviderKey(String userEmail, String providerKey);

}
