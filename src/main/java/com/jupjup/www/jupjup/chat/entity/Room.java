package com.jupjup.www.jupjup.chat.entity;

import com.jupjup.www.jupjup.giveaway.entity.Giveaway;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public void addUserChatRoom(Long userId) {
        UserChatRoom userChatRoom = UserChatRoom.builder()
                .userId(userId)
                .room(this)
                .build();

        userChatRooms.add(userChatRoom);
    }
}
