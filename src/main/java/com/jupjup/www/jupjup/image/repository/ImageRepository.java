package com.jupjup.www.jupjup.image.repository;

import com.jupjup.www.jupjup.image.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
}
