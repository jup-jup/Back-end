package com.jupjup.www.jupjup.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @fileName      : MyPageList.java
 * @author        : boramkim
 * @since         : 2024. 8. 12.
 * @description    : 16번 mysql 나눔 리스트 entity
 */

@Entity
@NoArgsConstructor
@Getter @Setter
public class MyPageList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String imageUrl;

    private String writingForumPick;  // 글쓰기_포럼픽
    private String writingBeautiful;  // 글쓰기_아름
    private String description;       // 설명
    private String representativeImage; // 대표이미지

    private String registrationDate;  // 등록날짜
    private String desiredTradeLocation; // 희망거래장소 위치(00동)
    private String reservationStatus; // 예약여부 (예약중, 나눔완료, 나눔중)
    private int commentCount;         // 댓글수
    private int viewCount;            // 조회수
}
