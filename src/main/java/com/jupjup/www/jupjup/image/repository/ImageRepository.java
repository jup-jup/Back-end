package com.jupjup.www.jupjup.image.repository;

import com.jupjup.www.jupjup.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
