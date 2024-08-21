package com.jupjup.www.jupjup.domain.entity.chat;

import com.jupjup.www.jupjup.domain.entity.giveaway.Giveaway;
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

    @JoinColumn(name = "giveaway_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Giveaway.class, fetch = FetchType.LAZY)
    private Giveaway giveaway;

    @Column(name = "giveaway_id")
    private Long giveawayId;

    @CreatedDate
    private LocalDateTime created_at;

}
