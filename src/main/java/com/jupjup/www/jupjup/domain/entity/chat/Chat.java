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

    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "creator_id")
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "room_id")
    private Room room;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime created_at;

}
