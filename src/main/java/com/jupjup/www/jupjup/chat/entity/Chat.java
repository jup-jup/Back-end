package com.jupjup.www.jupjup.chat.entity;


import com.jupjup.www.jupjup.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Table(name = "chat")
@Getter
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "content")
    private String content;

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

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public Chat(String content, Long userId, Long roomId) {
        this.content = content;
        this.userId = userId;
        this.roomId = roomId;
    }

}
