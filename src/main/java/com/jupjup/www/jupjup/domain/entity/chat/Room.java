package com.jupjup.www.jupjup.domain.entity.chat;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "room")
@Getter
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @OneToMany(mappedBy = "room")
    private List<UserChatRoom> userChatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Chat> chats = new ArrayList<>();

    @CreatedDate
    private LocalDateTime created_at;

}
