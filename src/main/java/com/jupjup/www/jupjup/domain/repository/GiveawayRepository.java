package com.jupjup.www.jupjup.domain.repository;

import com.jupjup.www.jupjup.domain.entity.giveaway.Giveaway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiveawayRepository extends JpaRepository<Giveaway, Long> {
}
