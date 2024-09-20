package com.jupjup.www.jupjup.giveaway.entity;

import com.jupjup.www.jupjup.chat.entity.Room;
import com.jupjup.www.jupjup.giveaway.enums.GiveawayStatus;
import com.jupjup.www.jupjup.image.entity.Image;
import com.jupjup.www.jupjup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "giveaway")
public class Giveaway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GiveawayStatus status = GiveawayStatus.PENDING;

    @OneToMany(mappedBy = "giveaway")
    private List<Room> chatRooms = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JoinColumn(name = "giver_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User giver;

    @Column(name = "giver_id")
    private Long giverId;

    @JoinColumn(name = "receiver_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User receiver;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

    // cascade
    // orphanRemoval: 부모와 자식간의 연관관계를 제거하면 자식 엔티티가 고아가 되어 DB 에서 삭제됨
    @OneToMany(mappedBy = "giveaway", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<Image> images = new ArrayList<>();

    @Column(name = "location")
    private String location;

    @ColumnDefault("0")
    @Column(name = "view_count")
    private Long viewCount; // 조회수

    // 나눔 저장될 때마다 조회수 기본값 0으로 세팅
    @PrePersist
    public void prePersist() {
        this.viewCount = this.viewCount == null ? 0L : this.viewCount;
    }

    @Builder
    public Giveaway(String title, String description, Long giverId, List<Image> images, String location) {
        this.title = title;
        this.description = description;
        this.giverId = giverId;
        this.images = images;
        this.location = location;

        for (Image image : images) {
            updateImage(image);
        }
    }

    public void update(String title, String description, List<Image> images) {
        this.title = title;
        this.description = description;

        // 제거된 이미지 연관관계 제거
        List<Image> removedImages = this.images.stream()
                .filter(v -> !images.contains(v))
                .toList();
        for (Image removedImage : removedImages) {
            removedImage.removeGiveawayMapping();
        }

        // 이미지 업데이트
        this.images = images;
        for (Image image : images) {
            updateImage(image);
        }

    }

    public void updateStatus(GiveawayStatus status, Long receiverId) {
        if (receiverId.equals(this.getGiverId())) {
            return;
        }

        switch (status) {
            case PENDING -> {
                this.receiverId = null;
            }
            case RESERVED -> {
                this.receiverId = receiverId;
            }
            case COMPLETED -> {
                this.receiverId = receiverId;
                this.receivedAt = LocalDateTime.now();
            }
        }

        this.status = status;

    }

    public boolean validateUpdateStatus(GiveawayStatus status, Long userId) {
        if (status.equals(GiveawayStatus.COMPLETED) && this.status.equals(GiveawayStatus.PENDING)) {
            return false;
        }

        // 나눔 유저는 모든 상태 변경 가능
        if (userId.equals(this.getGiverId())) {
            return true;
        }

        // 대기, 예약 상태는 나눔 유저만 가능
        // 완료 상태라면, 나눔 유저나 예약자인 경우 업데이트 가능
        return status.equals(GiveawayStatus.COMPLETED) && userId.equals(this.getReceiverId());
    }

    public void updateImage(Image image) {
        image.mappingImage(this);
    }

    public void updateViewCnt() {
        this.viewCount++;
    }

}
