package com.jupjup.www.jupjup.giveaway.repository;

import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import com.jupjup.www.jupjup.giveaway.enums.GiveawayStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GiveawayRepository extends JpaRepository<Giveaway, Long> {
    // 모든 유저 나눔 리스트
    @Query(value = "SELECT g FROM Giveaway g JOIN FETCH g.giver LEFT JOIN FETCH g.chatRooms",
            countQuery = "SELECT COUNT(g) FROM Giveaway g")
    Page<Giveaway> findAllGiveawaysWithUsersAndRooms(Pageable pageable);

    // 나눔 검색 리스트
    @Query(value = "SELECT g.created_at, g.giver_id, g.id, g.updated_at, g.view_count, g.description, g.location, g.title, g.status, g.received_at, g.receiver_id " +
            "FROM giveaway g JOIN user u on g.giver_id = u.id LEFT JOIN room r on g.id = r.giveaway_id " +
            "WHERE MATCH(g.title, g.description) AGAINST(:keyword IN BOOLEAN MODE)",
            countQuery = "SELECT COUNT(*) FROM giveaway g JOIN user u on g.giver_id = u.id LEFT JOIN room r on g.id = r.giveaway_id " +
                    "WHERE MATCH(g.title, g.description) AGAINST(:keyword IN BOOLEAN MODE)",
            nativeQuery = true)
    Page<Giveaway> findAllByKeywordWithUsersAndRooms(@Param("keyword") String keyword, Pageable pageable);

    // 특정 유저 나눔 리스트로 내려줌
    @Query(value = "SELECT g FROM Giveaway g JOIN FETCH g.giver u WHERE u.id = :userId and g.status !=:status ",
            countQuery = "SELECT COUNT(g) FROM Giveaway g JOIN g.giver u WHERE u.id = :userId and g.status !=:status")
    Page<Giveaway> findAllByGiverId(Pageable pageable, @Param("userId") Long userId, @Param("status") GiveawayStatus status);

    // 특정 유저 받은내역 리스트로 내려줌
    @Query(value = "SELECT g FROM Giveaway g JOIN FETCH g.giver u WHERE u.id = :userId and g.status =:status",
            countQuery = "SELECT COUNT(g) FROM Giveaway g JOIN g.giver u WHERE u.id = :userId and g.status =:status")
    Page<Giveaway> findAllByUsersAndStatus(Pageable pageable, @Param("userId") Long userId, @Param("status") GiveawayStatus status);

}
