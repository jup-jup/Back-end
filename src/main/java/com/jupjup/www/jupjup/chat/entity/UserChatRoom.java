package com.jupjup.www.jupjup.chat.entity;

import com.jupjup.www.jupjup.domain.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Entity
@Table(name = "user_chat_room")
public class UserChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;

    @Column(name = "user_id")
    private Long userId;

    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY)
    private Room room;

    @Column(name = "room_id")
    private Long roomId;

    // TODO: last read message id 저장 필요
//    @Column(name = "last_read_chat_id")
//    private Long lastReadChatId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public UserChatRoom(Long roomId, Long userId) {
        this.roomId = roomId;
        this.userId = userId;
    }

}
