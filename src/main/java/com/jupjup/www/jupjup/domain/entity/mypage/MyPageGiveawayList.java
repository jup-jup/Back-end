package com.jupjup.www.jupjup.domain.entity.mypage;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

/**
 * 나눔 리스트 정보를 저장하는 엔티티.
 * @autor : boramkim
 * @since : 2024. 8. 12.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EnableJpaAuditing
@Table(name = "mypage_giveaway_list")
public class MyPageGiveawayList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;  // 대표 이미지 URL

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;  // 등록 날짜

    @LastModifiedDate
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;  // 업데이트 날짜

    @Column(name = "trade_location", nullable = false)
    private String tradeLocation; // 희망 거래 장소 위치 (00동)

    @Column(name = "reservation_status", nullable = false)
    private String reservationStatus;   // 예약 여부 (예약중, 나눔완료, 나눔중)

    @Column(name = "chat_count", nullable = false)
    private Long chatCount;  // 채팅 수

    @Column(name = "view_count", nullable = false)
    private Long viewCount;  // 조회 수

    @Builder
    public MyPageGiveawayList(Long id, String title, String content, String imageUrl , String tradeLocation) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tradeLocation = tradeLocation;
    }

}