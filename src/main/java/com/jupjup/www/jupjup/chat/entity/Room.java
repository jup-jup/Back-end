package com.jupjup.www.jupjup.chat.entity;

import com.jupjup.www.jupjup.domain.entity.User;
import com.jupjup.www.jupjup.domain.entity.giveaway.Giveaway;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    public void addUserChatRoom(Long userId) {
        UserChatRoom userChatRoom = UserChatRoom.builder()
                .userId(userId)
                .room(this)
                .build();

        userChatRooms.add(userChatRoom);
    }

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

    // TODO: last_chat_time 저장해야할듯
//    @Column(name = "last_chat_time")
//    @UpdateTimestamp
//    private LocalDateTime lastChatTime;

    @Builder
    public Room(Long giveawayId) {
        this.giveawayId = giveawayId;
    }
}
