package com.jupjup.www.jupjup.giveaway.repository;

import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
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

    @Query(value = "SELECT g FROM Giveaway g JOIN FETCH g.giver",
            countQuery = "SELECT COUNT(g) FROM Giveaway g")
    Page<Giveaway> findAllGiveawaysWithUsers(Pageable pageable);

    @Query(value = "SELECT g FROM Giveaway g JOIN FETCH g.giver u WHERE u.id = :userId",
          countQuery = "SELECT COUNT(g) FROM Giveaway g JOIN g.giver u WHERE u.id = :userId")
    Page<Giveaway> findGiveawaysByGiverName(Pageable pageable, @Param("userId") Long userId);

}
