package com.jupjup.www.jupjup.domain.entity;

import com.jupjup.www.jupjup.enumClass.GiveawayStatus;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "giveaway")
@Getter
@Entity
public class Giveaway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status")
    private GiveawayStatus status;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver_id")
    private User giver;

    // TODO: 이미지의 저장 구현 방법은 고민해볼 것
    // TODO: cascade & orphanRemoval 에 대한 이해 필요
    @OneToMany(mappedBy = "giveaway", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<GiveawayImage> imagePaths;

    // TODO: 거래 장소

    // TODO: 댓글 수 -> 관련 채팅 수인가?

    // TODO: 조회 수

}
