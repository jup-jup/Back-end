package com.jupjup.www.jupjup.domain.entity.chat;


import com.jupjup.www.jupjup.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime created_at;

}
