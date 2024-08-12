package com.jupjup.www.jupjup.domain.entity;

import com.jupjup.www.jupjup.domain.entity.chat.Chat;
import com.jupjup.www.jupjup.domain.entity.chat.UserChatRoom;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String providerKey;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String userEmail;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<UserChatRoom> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<Chat> chats = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        updatedAt = createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Builder
    public User(String providerKey, String username, String userEmail, String role) {
        this.providerKey = providerKey;
        this.name = username;
        this.userEmail = userEmail;
        this.role = role;
    }

}