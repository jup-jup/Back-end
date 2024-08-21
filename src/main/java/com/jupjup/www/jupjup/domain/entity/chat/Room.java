package com.jupjup.www.jupjup.domain.entity.chat;

import com.jupjup.www.jupjup.domain.entity.giveaway.Giveaway;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Table(name = "room")
@Getter
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // TODO: ManyToMany 를 UserChatRoom 엔티티로 분리해놨는데 어떻게 관리할 것인지 확인 필요.
    @OneToMany(mappedBy = "room")
    private List<UserChatRoom> userChatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Chat> chats = new ArrayList<>();

    @JoinColumn(name = "giveaway_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Giveaway.class, fetch = FetchType.LAZY)
    private Giveaway giveaway;

    @Column(name = "giveaway_id")
    private Long giveawayId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public Room(Long giveawayId) {
        this.giveawayId = giveawayId;
    }
}
