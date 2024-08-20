package com.jupjup.www.jupjup.domain.entity.giveaway;

import com.jupjup.www.jupjup.domain.entity.User;
import com.jupjup.www.jupjup.domain.entity.chat.Room;
import com.jupjup.www.jupjup.domain.enums.GiveawayStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Builder
    public Giveaway(String title, String description, Long giverId) {
        this.title = title;
        this.description = description;
        this.giverId = giverId;
    }

    @JoinColumn(name = "giver_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User giver;

    @Column(name = "giver_id")
    private Long giverId;

    // TODO: 이미지의 저장 구현 방법은 고민해볼 것
    // TODO: cascade & orphanRemoval 에 대한 이해 필요
//    @OneToMany(mappedBy = "giveaway", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    private List<GiveawayImage> imagePaths;

    // TODO: 거래 장소

    // TODO: 댓글 수 -> 관련 채팅 수인가?

    // TODO: 조회 수

}
