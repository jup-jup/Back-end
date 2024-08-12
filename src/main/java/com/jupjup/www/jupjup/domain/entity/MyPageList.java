package com.jupjup.www.jupjup.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 나눔 리스트 정보를 저장하는 엔티티.
 * @author : boramkim
 * @since : 2024. 8. 12.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class MyPageList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String title;
    private String content;
    private String description;

    @Column(nullable = false)
    private String imageUrl;  // 대표 이미지 URL

    @Column(nullable = false)
    private String registrationDate;  // 등록 날짜

    @Column(nullable = false)
    private String desiredTradeLocation; // 희망 거래 장소 위치 (00동)

    @Column(nullable = false)
    private String reservationStatus;   // 예약 여부 (예약중, 나눔완료, 나눔중)

    @Column(nullable = false)
    private int chatCount;  // 채팅 수

    @Column(nullable = false)
    private int viewCount;  // 조회 수
}