package com.jupjup.www.jupjup.domain.entity.mypage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

/**
 * 나눔 리스트 정보를 저장하는 엔티티.
 * @author : boramkim
 * @since : 2024. 8. 12.
 */
@Table(name="mypagalist")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EnableJpaAuditing
public class MyPageList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id" ,nullable = false)
    private Long id;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String imageUrl;  // 대표 이미지 URL

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;  // 등록 날짜
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;  // 업데이트 날짜
    @Column(nullable = false)
    private String tradeLocation; // 희망 거래 장소 위치 (00동)
    @Column(nullable = false)
    private String reservationStatus;   // 예약 여부 (예약중, 나눔완료, 나눔중)
    @Column(nullable = false)
    private int chatCount;  // 채팅 수
    @Column(nullable = false)
    private int viewCount;  // 조회 수

}