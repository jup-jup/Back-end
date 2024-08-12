package com.jupjup.www.jupjup.domain.entity.chat;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "room")
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @OneToMany
    private List<UserChatRoom> joinedUsers = new ArrayList<>();

    @CreatedDate
    private LocalDateTime created_at;

}
