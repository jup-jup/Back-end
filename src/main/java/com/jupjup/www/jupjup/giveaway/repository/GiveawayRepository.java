package com.jupjup.www.jupjup.giveaway.repository;

import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.enums.GiveawayStatus;
import com.jupjup.www.jupjup.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiveawayRepository extends JpaRepository<Giveaway, Long> {
    // 모든 유저 나눔 리스트
    @Query(value = "SELECT g FROM Giveaway g JOIN FETCH g.giver",
            countQuery = "SELECT COUNT(g) FROM Giveaway g")
    Page<Giveaway> findAllGiveawaysWithUsers(Pageable pageable);

    // 특정 유저 나눔 리스트로 내려줌
    @Query(value = "SELECT g FROM Giveaway g JOIN FETCH g.giver u WHERE u.id = :userId and g.status !=:status ",
          countQuery = "SELECT COUNT(g) FROM Giveaway g JOIN g.giver u WHERE u.id = :userId and g.status !=:status")
    Page<Giveaway> findAllByGiverId(Pageable pageable, @Param("userId") Long userId, @Param("status") GiveawayStatus status);

    // 특정 유저 받은내역 리스트로 내려줌
    @Query(value = "SELECT g FROM Giveaway g JOIN FETCH g.giver u WHERE u.id = :userId and g.status =:status",
            countQuery = "SELECT COUNT(g) FROM Giveaway g JOIN g.giver u WHERE u.id = :userId and g.status =:status")
    Page<Giveaway> findAllByUsersAndStatus(Pageable pageable, @Param("userId") Long userId , @Param("status") GiveawayStatus status);

}
