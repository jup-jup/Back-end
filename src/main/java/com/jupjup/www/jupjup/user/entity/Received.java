package com.jupjup.www.jupjup.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
@EnableJpaAuditing
@Table(name = "received_list")
public class Received {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "title", nullable = false)
    private String title; // 제목

    @Column(name = "content", nullable = false)
    private String content; // 내용

    @Column(name = "location", nullable = false)
    private String location; // 거래 장소

    @Column(name = "chat_count", nullable = false)
    private int chatCount; // 채팅 수

    @Column(name = "view_count", nullable = false)
    private int viewCount; // 조회수

    @Column(name = "representative_image", nullable = false)
    private String representativeImage; // 대표 이미지 URL 또는 경로

    @CreatedDate
    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt; // 받은 날짜

    @Column(name = "giver_name", nullable = false)
    private String giverName; // 나눔한 사람의 이름

}