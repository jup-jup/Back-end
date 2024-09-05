package com.jupjup.www.jupjup.user.entity;

import com.jupjup.www.jupjup.chat.entity.Chat;
import com.jupjup.www.jupjup.chat.entity.UserChatRoom;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "provider_key", nullable = false)
    private String providerKey;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<UserChatRoom> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Chat> chats = new ArrayList<>();

    @Builder
    public User(String providerKey, String userName, String userEmail) {
        this.providerKey = providerKey;
        this.userName = userName;
        this.userEmail = userEmail;
    }
}