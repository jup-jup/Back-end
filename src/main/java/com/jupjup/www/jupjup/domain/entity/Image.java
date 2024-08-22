package com.jupjup.www.jupjup.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // TODO: 어디에 저장할 것인지는 정할 필요가 있음 (AWS S3, Google Cloud Storage, 혹은 VM 에 볼륨 마운트해서 저장한다거나..)
    // 이미지 저장 경로
    @Column(name = "path", nullable = false)
    private String path;

    // 파일명
    @Column(name = "file_name")
    private String fileName;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
